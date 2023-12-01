package com.example.food_admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food_admin.Model.add_item
import com.example.food_admin.databinding.ActivityAddItemBinding
import com.example.food_admin.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class Add_item_activity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding

    private lateinit var foodname:String
    private lateinit var foodprice:String
    private lateinit var fooddisc:String
    private lateinit var foodingre:String
    private lateinit var foodID: String
    private  var foodimageuri: Uri?=null
    private  lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Initalizing authentication varubale
        auth = Firebase.auth
        // iNITITALIZING DATABASE VARIABLE
        database = FirebaseDatabase.getInstance()

        binding.add.setOnClickListener{
            foodname= binding.foodnamesign.text.toString().trim()
            fooddisc= binding.shortdisc.text.toString().trim()
            foodingre=binding.ingredi.text.toString().trim()
            foodprice= binding.phonenumber.text.toString().trim()

            if(!(foodname.isBlank() || fooddisc.isBlank() || foodingre.isBlank() || foodprice.isBlank() || foodimageuri == null))
            {
                uploadData()


                Toast.makeText(this, "Item Added Sucessfuly after upload ", Toast.LENGTH_SHORT).show();

                finish()

            }
            else{
                Toast.makeText(this, "Fill All Field", Toast.LENGTH_SHORT).show();


            }
        }

        binding.foodimage.setOnClickListener {
            pickimage.launch("image/*")
        }

        binding.imageButton.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun uploadData(){
            val menuRef: DatabaseReference = database.getReference("menu")

            val newItemKey: String? = menuRef.push().key

            if (foodimageuri != null && newItemKey != null) {
                val storageRef: StorageReference = FirebaseStorage.getInstance().reference
                val imageRef: StorageReference = storageRef.child("menu_images/${newItemKey}.jpg")
                val uploadTask: UploadTask = imageRef.putFile(foodimageuri!!)

                uploadTask.addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->

                        val newItem = add_item(newItemKey,foodname, foodprice, fooddisc, downloadUrl.toString(), foodingre,foodprice)
                        newItem.let {
                            menuRef.child(newItemKey).setValue(newItem) // Pass the newItemKey here
                            Toast.makeText(this, "Item Added Successfully from database", Toast.LENGTH_SHORT).show()
                            // This is where actually it is uploading in database

                        }
                    }
                }

        }
        else
            {
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show()


            }

    }

    val pickimage = registerForActivityResult(ActivityResultContracts.GetContent())
    {Uri->
        if(Uri!=null)
        {
            binding.foodimage.setImageURI(Uri)
            foodimageuri =Uri
        }
    }
}