package fr.isen.amadori.androiderestaurant.oders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.amadori.androiderestaurant.DetailsActivity
import fr.isen.amadori.androiderestaurant.databinding.OrdersDetailsBinding

class OrderAdapter(
    private val orders: List<Order>,
    private val details_orders: DetailsActivity
): RecyclerView.Adapter<OrderAdapter.OrderHolder>() {


    class OrderHolder(binding: OrdersDetailsBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val itemBinding = OrdersDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = orders.size

}