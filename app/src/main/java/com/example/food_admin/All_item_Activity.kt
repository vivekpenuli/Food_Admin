package com.example.food_admin

import MenuItemAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.DataModel.MenuItem
import com.example.food_admin.Model.add_item
import com.example.food_admin.databinding.ActivityAllItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class All_item_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityAllItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    val menuItem: ArrayList<add_item> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Initalizing authentication varubale
        auth = Firebase.auth
        // iNITITALIZING DATABASE VARIABLE
        database = FirebaseDatabase.getInstance()

        retreiveitem()

        binding.imageButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupRecyclerView()

    }

    private fun retreiveitem() {
        val foodref: DatabaseReference = database.reference.child("menu")

        foodref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItem.clear()
                for (foodsnapshot in snapshot.children) {
                    val menuItemData = foodsnapshot.getValue(add_item::class.java)
                    if (menuItemData != null) {
                        menuItem.add(menuItemData)
                    }
                }
                setupRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.allitem
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MenuItemAdapter(menuItem)
        recyclerView.adapter = adapter

        val databaseReference = database.reference.child("menu")

        adapter.onDeleteClickListener = { position ->
            val item = adapter.dataSet[position]
            val foodName = item.foodId // Assuming foodName is unique
/// Always use Id for refernce never use other thing
            // Remove the item from Firebase Realtime Database using food name as the key
            databaseReference.child(foodName!!).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Remove the item from the RecyclerView
                        adapter.dataSet.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    } else {
                        // Handle the error when deleting from Firebase
                        Log.e("FirebaseDelete", "Failed to delete: ${task.exception}")
                    }
                }
        }

    }
}