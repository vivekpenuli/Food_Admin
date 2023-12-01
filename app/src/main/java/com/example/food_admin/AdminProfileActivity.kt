package com.example.food_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.food_admin.Model.User
import com.example.food_admin.databinding.ActivityAdminProfileBinding
import com.example.food_admin.databinding.ActivityCompletedOnClickBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfileBinding
private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(androidx.appcompat.R.color.material_blue_grey_900)
setUserData()

    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if(userId!=null)
        {
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        val userProfile =snapshot.getValue(User::class.java)
                        if(userProfile!=null)
                        {
                            binding.userprifilename.text = userProfile.name
                            binding.profielemail.text = userProfile.email
                            binding.profilephone.text = userProfile.phone
                            binding.profilerest.text = userProfile.restaurentname

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}