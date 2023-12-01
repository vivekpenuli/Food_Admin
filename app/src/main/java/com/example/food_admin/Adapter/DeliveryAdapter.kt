// DeliveryAdapter.kt

package com.example.food_admin.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_admin.databinding.DeliveryItemRecycleBinding

class DeliveryAdapter(
    private val customerName: MutableList<String>,
    private val payment: MutableList<Boolean>
) : RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveryItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customerName[position]
        val isPaymentReceived = payment[position]
        holder.bind(customer, isPaymentReceived)
    }

    override fun getItemCount(): Int {
        return customerName.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: DeliveryItemRecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(customerName: String, isPaymentReceived: Boolean) {
            binding.textView1.text = customerName
            binding.textView3.text = if (isPaymentReceived) "Received" else "Pending"
            val textColor = if (isPaymentReceived) Color.GREEN else Color.RED
            binding.textView3.setTextColor(textColor)
            binding.deliverystatus.backgroundTintList = ColorStateList.valueOf(textColor)

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
    }
}
