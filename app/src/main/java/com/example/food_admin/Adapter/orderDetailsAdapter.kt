package com.example.food_admin.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_admin.databinding.OrderDetailsItemBinding

class orderDetailsAdapter(
    private  var context: Context,
    private var foodNamess: MutableList<String> = mutableListOf(),
            private var foodImagess : MutableList<String> = mutableListOf(),
            private var foodQuantitys : MutableList<Int> = mutableListOf(),
            private var foodPrices: MutableList<String> = mutableListOf(),
) :RecyclerView.Adapter<orderDetailsAdapter.orderDetailsViewHolder>(){
  inner  class orderDetailsViewHolder(private val binding: OrderDetailsItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
binding.apply {
    foodname.text = foodNamess[position]
    foodquant.text = foodQuantitys[position].toString()
    foodprice.text = foodPrices[position]
    val uriString = foodImagess[position]
    val uri = Uri.parse(uriString)
    Glide.with(context).load(uri).into(foodimag)
}
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): orderDetailsAdapter.orderDetailsViewHolder {
val binding = OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

return  orderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: orderDetailsAdapter.orderDetailsViewHolder,
        position: Int
    ) {
holder.bind(position)
    }

    override fun getItemCount(): Int {
return foodNamess.size   }

}