package fr.isen.amadori.androiderestaurant.oders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.OrderItemBinding

class OrderAdapter(
    private val orders: MutableList<OrderInfo>,
    private val buttonToFinaliseOrder: Button,
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
        buttonToFinaliseOrder.text = "Payer :" + getPriceTotalOrder(Order(orders)).toString() + "€"
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
            buttonToFinaliseOrder.text = "Payer :" + getPriceTotalOrder(Order(orders)).toString() + "€"
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = orders.size

    fun getPriceTotalOrder(order: Order): Double{
        var total: Double = 0.0
        for (i in order.orders){
            total += (i.dish.getPrice()*i.quantity)
        }
        return total
    }

    fun deleteOrder(position: Int) {
        if (orders[position].quantity != 1) {
            orders[position].quantity--
        } else {
            orders.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

