package com.example.food_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.Adapter.orderDetailsAdapter
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.databinding.ActivityCustomerOrderDeliveryBinding
import com.example.food_admin.databinding.ActivityPendinOnclickBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class customerOrderDelivery : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerOrderDeliveryBinding
    private  var userName : String ? = null
    private var phonenumber : String ? = null
    private var totalPrice: String ? = null
    private lateinit var database: FirebaseDatabase
    private var OrderId : String ?= null
    private var foodNames: MutableList<String> = mutableListOf()
    private var foodImages : MutableList<String> = mutableListOf()
    private var foodQuantity : MutableList<Int> = mutableListOf()
    private var foodPrice: MutableList<String> = mutableListOf()
    private  var userId : String ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerOrderDeliveryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = FirebaseDatabase.getInstance()
        OrderId = intent.getStringExtra("orderId")


        binding.accepted.setOnClickListener {
            // Update the Firebase variable "orderaccepted" to true

                orderDispatched()

        }
        getdataFromIntent()
    }

    private fun orderDispatched() {

        val dispatch = OrderId

        val time = System.currentTimeMillis()

        val orderDetails =
            OrderDetials(userId,userName,foodNames,foodImages,foodPrice,foodQuantity,totalPrice,
            phonenumber,true,true,OrderId,time
        )

        val buyHistoryReference = database.reference.child("App_user").child(userId!!).child("BuyHistory").child(OrderId!!)
        buyHistoryReference.child("paymnetRecieved").setValue(true)

// Color Button

        // Order when done will be present in another node
        val dispatchOrderReference = database.reference.child("Done").child(dispatch!!)
        dispatchOrderReference.setValue(orderDetails).addOnSuccessListener {
            deleteItem()
        }

    }

    private fun deleteItem() {
        val orderdelete = database.reference.child("CompletedOrder").child(OrderId!!)
        orderdelete.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Order is Completed Done", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(this,"Order is Not Dispatched", Toast.LENGTH_SHORT).show()

            }
    }


    private fun getdataFromIntent() {
        userName = intent.getStringExtra("username")
        phonenumber = intent.getStringExtra("phone")
        totalPrice = intent.getStringExtra("totalprice")
        val stringListfoodname = intent.getStringExtra("foodNames")
        foodNames = stringListfoodname?.split(",")?.toMutableList()!!


        val stringListfoodimg = intent.getStringExtra("foodimg")
        foodImages = stringListfoodimg?.split(",")?.toMutableList()!!

        foodQuantity= intent.extras?.getIntegerArrayList("foodquan")!!

        val stringListfoodprice = intent.getStringExtra("foodprice")
        foodPrice = stringListfoodprice?.split(",")?.toMutableList()!!
        userId = intent.getStringExtra("userUid")

        setuserDetails()
        setAdapter()
    }

    private fun setAdapter() {
        binding.orderrecylcer.layoutManager = LinearLayoutManager(this)
        val adapter = orderDetailsAdapter(this,foodNames,foodImages,foodQuantity,foodPrice)
        binding.orderrecylcer.adapter= adapter
    }

    private fun setuserDetails() {
        binding.ordername.text = userName
        binding.orderphone.text = phonenumber
        binding.orderquant.text = foodQuantity.sum().toString()
        binding.orderprice2.text = totalPrice
    }


}
