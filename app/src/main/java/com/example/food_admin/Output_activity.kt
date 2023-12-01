package com.example.food_admin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.Adapter.DeliveryAdapter
import com.example.food_admin.Adapter.newDeliveryAdapter
import com.example.food_admin.DataModel.DeliveryItem
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.databinding.ActivityMainBinding
import com.example.food_admin.databinding.ActivityOutputBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Output_activity : AppCompatActivity() {

    private lateinit var binding: ActivityOutputBinding
    private lateinit var database: FirebaseDatabase
    private var  listofCompletedOrder: ArrayList<OrderDetials> = arrayListOf()
    private val filteredItems: ArrayList<OrderDetials> = ArrayList() // To store filtered items


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOutputBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = FirebaseDatabase.getInstance()


        binding.imageButton.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setupRecyclerView()

        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter items as the user types
                filterItems(newText.orEmpty())
                return true
            }
        })

        retrievecompleteorderDetails()


    }

    private fun retrievecompleteorderDetails() {
        val completeOrderReference = database.reference.child("CompletedOrder")

        completeOrderReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
listofCompletedOrder.clear()
                for (ordersnapshot in snapshot.children)
                {
                    val completeorder = ordersnapshot.getValue(OrderDetials::class.java)
                    completeorder?.let{
                        listofCompletedOrder.add(it) }
                }
                filterItems("") // Initialize with all items


            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun setupRecyclerView() {

        val recyclerView = binding.outputrecylerview
        val customername = mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()

        for (order in listofCompletedOrder)
        {
            order.userName?.let{
                customername.add(it)
            }
            moneyStatus.add(order.paymnetRecieved)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = newDeliveryAdapter(listofCompletedOrder)
        recyclerView.adapter = adapter
adapter.setOnItemClickListener(object : newDeliveryAdapter.OnItemClickListener{
    override fun onItemClick(position: Int, item: OrderDetials) {
        val intent = Intent(this@Output_activity, customerOrderDelivery::class.java)
        val bundle = Bundle()
        bundle.putString("username",item.userName)
        //    bundle.putString("firebaseKey", item.firebaseKey)
        val stringList = item.foodNames?.joinToString(",")
        bundle.putString("foodNames", stringList)

        bundle.putString("userUid",item.userUid)
        val stringListimage = item.foodImages?.joinToString(",")
        bundle.putString("foodimg",stringListimage)

        bundle.putIntegerArrayList("foodquan",
            item.foodQuantites?.let { ArrayList(it) })

        bundle.putString("orderId",item.itemPushKey)

        bundle.putString("phone",item.phoneNumber)

        val stringListprice = item.foodPrices?.joinToString(",")
        bundle.putString("foodprice",stringListprice)
        // Attach the Bundle to the Intent

        bundle.putString("totalprice",item.totalPrices)
        // Attach the Bundle to the Intent
        intent.putExtras(bundle)
        startActivity(intent)
    }

})
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterItems(query: String) {
        filteredItems.clear()
        for (item in listofCompletedOrder) {
            val phoneNumber = item.userName
            if (phoneNumber != null) {
                if (phoneNumber.contains(query, ignoreCase = true)) {
                    filteredItems.add(item)
                }
            }
        }
        binding.outputrecylerview.adapter?.notifyDataSetChanged()

    }
}