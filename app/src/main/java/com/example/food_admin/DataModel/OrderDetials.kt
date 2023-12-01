package com.example.food_admin.DataModel

data class OrderDetials(
    var userUid: String?= null,
    var userName : String?=null,
    var foodNames: MutableList<String>?=null,
    var foodImages: MutableList<String>? = null,
    var foodPrices : MutableList<String>?= null,
    var foodQuantites: MutableList<Int>? = null,
    var totalPrices : String?=null,
    var phoneNumber:String?=null,
    var orderAccepted:Boolean = false,
    var paymnetRecieved : Boolean = false,
    var itemPushKey : String?=null,
    var currentTime:Long =0
)
