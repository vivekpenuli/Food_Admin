import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_admin.Model.add_item
import com.example.food_admin.databinding.CartitemBinding



// Replace with your actual package name

class MenuItemAdapter(val dataSet: MutableList<add_item>) :
    RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    var onDeleteClickListener: ((Int) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataSet.size

  inner  class ViewHolder(private val binding: CartitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: add_item) {
            binding.foodname.text = item.foodName
            binding.foodprice.text = item.foodPrice
            binding.foodquant.text = item.foodDisc.toString()
            val uriString: String = item.foodImg.toString()
            val uri: Uri = Uri.parse(uriString)
            Glide.with(binding.root.context) // Use the context from the root view
                .load(uri)
                .into(binding.foodimag) // Replace 'imageView' with your actual ImageView ID

            binding.deleteImageView.setOnClickListener {
                onDeleteClickListener?.invoke(position)
            }
        }
    }
}



