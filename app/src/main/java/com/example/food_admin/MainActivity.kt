package com.example.food_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food_admin.databinding.ActivityLoginBinding
import com.example.food_admin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
private lateinit var database:FirebaseDatabase
private lateinit var auth : FirebaseAuth
private lateinit var  completedOrderReference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

            binding.AddMenu.setOnClickListener {

            val intent = Intent(this,Add_item_activity::class.java)
            startActivity(intent)
        }
        binding.AllitemMenu.setOnClickListener {

            val intent = Intent(this,All_item_Activity::class.java)
            startActivity(intent)
        }

        binding.deliverystatus.setOnClickListener {

            val intent = Intent(this,Output_activity::class.java)
            startActivity(intent)
        }

        binding.profile.setOnClickListener {

            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.cardViewPendingOrders.setOnClickListener {

            val intent = Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }

        binding.cardViewCompletedOrders.setOnClickListener{

            val intent = Intent(this,Completed_on_click_Activity::class.java)
            startActivity(intent)
        }
binding.logout.setOnClickListener{
    auth.signOut()
    val intent = Intent(this, Login_activity::class.java)
    startActivity(intent)
    finish()
}
pendingOrders()
        completedOrder()

    }

    private fun completedOrder() {
        database = FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("Done")

        // Create a ValueEventListener to listen for changes
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.CompleteOrdersCount.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here if needed
            }
        }

        // Add the ValueEventListener to the reference
        pendingOrderReference.addValueEventListener(valueEventListener)
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("OrderDetails")

        // Create a ValueEventListener to listen for changes
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrdersCount.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here if needed
            }
        }

        // Add the ValueEventListener to the reference
        pendingOrderReference.addValueEventListener(valueEventListener)
    }

}