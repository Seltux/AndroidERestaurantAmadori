package fr.isen.amadori.androiderestaurant.oders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.amadori.androiderestaurant.DetailsActivity
import fr.isen.amadori.androiderestaurant.FinalOrderActivity
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.ActivityFinalOrderBinding
import fr.isen.amadori.androiderestaurant.databinding.OrderItemBinding
import fr.isen.amadori.androiderestaurant.databinding.OrdersDetailsBinding
import fr.isen.amadori.androiderestaurant.model.Dish

class OrderAdapter(
    private val orders: MutableList<OrderInfo>,
    private val deleteOrderClickListener: (OrderInfo) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderHolder>() {


    class OrderHolder(binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val layout = binding.root
        val title = binding.idNomRepas
        val prixTotal = binding.idPrixTotalItem
        val totalQuantity = binding.idNombreItem
        val delete = binding.idDeleteItem
        val image_repas = binding.idImageRepas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val itemBinding =
            OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.title.text = orders[position].dish.title
        holder.prixTotal.text =
            (orders[position].quantity * orders[position].dish.getPrice()).toString() + "€"
        holder.totalQuantity.text = "x" + orders[position].quantity.toString()
        if (orders[position].dish.getFirstImage() != null) {
            Picasso.get().load(orders[position].dish.getFirstImage())
                .placeholder(R.drawable.logo_restaurant).error(R.drawable.jokes_about_italians)
                .into(holder.image_repas)
        } else {
            Picasso.get().load(R.drawable.jokes_about_italians).into(holder.image_repas)
        }
        holder.delete.setOnClickListener {
            deleteOrder(position)
            deleteOrderClickListener.invoke(orders[position])
        }
    }

    override fun getItemCount() = orders.size

    fun deleteOrder(position: Int) {
        if (orders[position].quantity != 1) {
            orders[position].quantity--
        } else {
            orders.removeAt(position)
        }
    }
}

