package com.example.food_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.Adapter.orderDetailsAdapter
import com.example.food_admin.DataModel.MenuItem
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.databinding.ActivityPendinOnclickBinding
import com.example.food_admin.databinding.ActivityPendingOrderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Objects

class PendinOnclickActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendinOnclickBinding

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
        binding = ActivityPendinOnclickBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        database = FirebaseDatabase.getInstance()
        OrderId = intent.getStringExtra("orderId")


val me =database.reference.child("OrderDetails").child(OrderId!!)
    me.addValueEventListener( object: ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        if (snapshot.exists())
        {
            if(snapshot.child("orderAccepted").value as Boolean)
            {
                binding.accepted.text="Dispatch"
            }
            else
            {

                binding.accepted.text="Done"

            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }


})

        binding.accepted.setOnClickListener {
            // Update the Firebase variable "orderaccepted" to true
            if(binding.accepted.text.equals("Done"))
            {

                orderAccepted()
                // Change the text of the button to "Dispatch"
                binding.accepted.text = "Dispatch"
            }
            else{
                orderDispatched()

            }


        }
        getdataFromIntent()
    }

    private fun orderDispatched() {

        val dispatch = OrderId

        val time = System.currentTimeMillis()

        val orderDetails =OrderDetials(userId,userName,foodNames,foodImages,foodPrice,foodQuantity,totalPrice,
            phonenumber,true,false,OrderId,time
        )


        val buyHistoryReference = database.reference.child("App_user").child(userId!!).child("BuyHistory").child(OrderId!!)
        buyHistoryReference.child("readytopick").setValue(true)

// Color Button

        val dispatchOrderReference = database.reference.child("CompletedOrder").child(dispatch!!)
        dispatchOrderReference.setValue(orderDetails).addOnSuccessListener {
            deleteItem()
        }

    }

    private fun deleteItem() {
        val orderdelete = database.reference.child("OrderDetails").child(OrderId!!)
        orderdelete.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Order is Dispatched",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(this,"Order is Not Dispatched",Toast.LENGTH_SHORT).show()

            }
    }

    private fun orderAccepted() {
val childitemPushKey = OrderId
        val clickOrderRefernce = childitemPushKey?.let {
            database.reference.child("OrderDetails").child(it)

        }
        clickOrderRefernce?.child("orderAccepted")?.setValue(true)

        val buyHistoryReference = database.reference.child("App_user").child(userId!!).child("BuyHistory").child(OrderId!!)
        buyHistoryReference.child("orderAccepted").setValue(true)

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