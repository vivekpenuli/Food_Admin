package com.example.food_admin.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_admin.DataModel.OrderDetials
import com.example.food_admin.databinding.DeliveryItemRecycleBinding

class newDeliveryAdapter(
    private val dataSet: MutableList<OrderDetials>) :
    RecyclerView.Adapter<newDeliveryAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: OrderDetials)
    }
    private var itemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DeliveryItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(private val binding: DeliveryItemRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderDetials) {
            binding.textView1.text = item.userName
            binding.textView3.text = if (item.paymnetRecieved) "Received" else "Pending"
            val textColor = if (item.paymnetRecieved) Color.GREEN else Color.RED
            binding.textView3.setTextColor(textColor)
            binding.deliverystatus.backgroundTintList = ColorStateList.valueOf(textColor)

            // Set click listener for the item view
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position, item)
                }
            }
        }
    }
}
