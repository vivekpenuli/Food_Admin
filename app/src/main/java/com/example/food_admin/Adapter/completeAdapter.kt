package com.example.food_admin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.DataModel.RecentlypurchaseAdapter
import com.example.food_admin.databinding.PendingOrderRecyclerviewBinding

class completeAdapter (
    private val dataSet: MutableList<OrderDetials>) :
    RecyclerView.Adapter<completeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PendingOrderRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataSet.size

    inner  class ViewHolder(private val binding: PendingOrderRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isAccepted = false

        fun bind(item: OrderDetials) {

            binding.orderuser.text = item.userName
            val totalQuantity = item.foodQuantites?.sum()
            binding.orderprice.text= item.totalPrices
            binding.orderquantity.text = totalQuantity.toString()

        }
    }
}