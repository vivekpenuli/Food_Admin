package com.example.food_admin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.DataModel.RecentlypurchaseAdapter
import com.example.food_admin.databinding.ActivityPendingOrderBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding
    private  lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    val recentlybuyitemlist:ArrayList<OrderDetials> = ArrayList()

    var container: ShimmerFrameLayout? = null
    private  lateinit var shimeerLayout: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        shimeerLayout = binding.simmereffect
container =binding.shimmerViewContainer

        binding.imageButton.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        Rretreiveitem()




    }


    private fun Rretreiveitem() {

        // Initalizing authentication varubale

        // iNITITALIZING DATABASE VARIABLE
        database = FirebaseDatabase.getInstance()
val foodref= database.reference.child("OrderDetails")
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

        adapter.setOnAcceptButtonClickListener(object : RecentlypurchaseAdapter.OnAcceptButtonClickListener {
            override fun onAcceptButtonClick(item: OrderDetials) {
                // Handle the "Accept" button click for the specific item
                // For example, you can open a new activity or perform any desired action
                val intent = Intent(this@PendingOrderActivity, PendinOnclickActivity::class.java)
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

    override fun onPause() {
        container?.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        container?.startShimmer()
        super.onResume()
    }

}