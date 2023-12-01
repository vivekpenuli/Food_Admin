package com.example.food_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.food_admin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Login_activity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private  lateinit var auth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Initalizing authentication varubale
        auth = Firebase.auth
        // iNITITALIZING DATABASE VARIABLE
        database = Firebase.database.reference



        binding.login.setOnClickListener {
            email = binding.emailAdress.text.toString()
            password = binding.pass.text.toString().trim()

         if(email.isBlank() || password.isBlank())
         {
             Toast.makeText(this, "Fill All Field", Toast.LENGTH_SHORT).show();

         }
            else{
               logintoaccount(email,password)
         }


        binding.register.setOnClickListener {

            val intent = Intent(this,Register_activity::class.java)
            startActivity(intent)
        }
    }

    }

    private fun logintoaccount(email: String, password: String) {
auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
    task ->
    if (task.isSuccessful)
    {
        // Login successful, navigate to the main activity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    } else {
        // Login failed, display an error message
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }
}
    }

// Used When we want to show in fron of teacher
   override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}


