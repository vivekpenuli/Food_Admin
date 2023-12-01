package com.example.food_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.DataModel.RecentlypurchaseAdapter
import com.example.food_admin.databinding.ActivityCompletedOnClickBinding
import com.example.food_admin.databinding.ActivityPendingOrderBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Completed_on_click_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCompletedOnClickBinding
    private  lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    val recentlybuyitemlist:ArrayList<OrderDetials> = ArrayList()

    var container: ShimmerFrameLayout? = null
    private  lateinit var shimeerLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompletedOnClickBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        shimeerLayout = binding.simmereffect
        container =binding.shimmerViewContainer

        Rretreiveitem()


    }
    private fun Rretreiveitem() {

        // Initalizing authentication varubale

        // iNITITALIZING DATABASE VARIABLE
        database = FirebaseDatabase.getInstance()
        val foodref= database.reference.child("Done")
        foodref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recentlybuyitemlist.clear()
                for (foodsnapshot in snapshot.children) {
                    val menuItemData: OrderDetials? = foodsnapshot.getValue(OrderDetials::class.java)
                    if (menuItemData != null) {
                        recentlybuyitemlist.add(menuItemData)
                    }

                }
                setupRecyclerView()
                Handler(Looper.getMainLooper()).postDelayed({
                    container?.stopShimmer()
                    shimeerLayout.visibility = View.GONE
                }, 3000)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun setupRecyclerView() {
        val recyclerView = binding.pendingrecylcerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecentlypurchaseAdapter(recentlybuyitemlist)
        recyclerView.adapter = adapter

        container?.stopShimmer()
        shimeerLayout.visibility = View.GONE


    }

    override fun onPause() {
        container?.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        container?.startShimmer()
        super.onResume()
    }
}