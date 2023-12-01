package com.example.food_admin.DataModel

import android.content.Context
import android.media.Image

data class pendingmodel(
    private val context: Context,
    private  val customerNames: MutableList<String>,
    private val quantity : MutableList<String>,
    private val foodImage: MutableList<String>
)
