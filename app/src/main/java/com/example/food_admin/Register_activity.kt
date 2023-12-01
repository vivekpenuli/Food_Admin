package com.example.food_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.food_admin.Model.User
import com.example.food_admin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Register_activity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private  lateinit var auth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password: String
    private lateinit var username : String
    private lateinit var nameofRestaurent : String
    private lateinit var database: DatabaseReference
    private lateinit var phone : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initalizing authentication varubale
        auth = Firebase.auth
        // iNITITALIZING DATABASE VARIABLE
        database = Firebase.database.reference


        binding.add.setOnClickListener {

            username = binding.usernamesign.text.toString().trim()
            email = binding.emailsign.text.toString()
            password = binding.passwordsign.text.toString().trim()
            phone = binding.phonenumber.text.toString()
            nameofRestaurent = binding.yearofjoin.text.toString()
            if (validateField()) {
                Toast.makeText(this, "Fill All Field", Toast.LENGTH_SHORT).show();
            } else {
                createAccount(email, password)

            }
        }
    }

    private fun createAccount(email: String, password: String) {


auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
    if(task.isSuccessful)
    {
        Toast.makeText(this, "Acoount created sucessfully", Toast.LENGTH_SHORT).show();
        saveuserData()

        val intent = Intent(this,Login_activity::class.java)
        startActivity(intent)
        finish()
    }
    else{
        Toast.makeText(this, "Acoount Creation Failed", Toast.LENGTH_SHORT).show();

    }
}
    }

    private fun saveuserData() {
        val user = User(
            username , nameofRestaurent,email,phone)
        val userId :String = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }

    private fun validateField(): Boolean {
        var flag = 0


        if (username.isEmpty()) {
            binding.usernamesign.error = "Enter Name"
            flag = 1
        }
if (nameofRestaurent.isEmpty())
{
    binding.yearofjoin.error="Enter Restaurent name"
}
        if (email.isEmpty()) {
            binding.emailsign.error = "Enter Email"
            flag = 1
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailsign.error = "Enter Valid Email"
            flag = 1
        }

        if (phone.isEmpty()) {
            binding.phonenumber.error = "Enter Phone number"
            flag = 1
        } else if (phone.length != 10) {
            binding.phonenumber.error = "Phone number should be 10 digits"
            flag=1
        }

        if (password.isEmpty()) {
            binding.passwordsign.error = "Enter Password"
            flag = 1
        } else if (password.length < 6) {
            binding.passwordsign.error = "Password Length should be greater than 6"
            flag = 1
        }

        return flag == 1
    }



    ///////// jO B function banega wo esh bracket ke under banega
}



