package fr.isen.amadori.androiderestaurant.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.amadori.androiderestaurant.databinding.HistoryOrderBinding
import fr.isen.amadori.androiderestaurant.order.Order

class ProfileAdapter(
    private val history: List<History>
): RecyclerView.Adapter<ProfileAdapter.ProfileHolder>() {

    class ProfileHolder(binding: HistoryOrderBinding): RecyclerView.ViewHolder(binding.root){
        val layout = binding.root
        val prixTotal = binding.idPrixTotalItem
        val totalQuantity = binding.idNombreItem
        val date = binding.idDateHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        val itemBinding =
            HistoryOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        val result = Gson().fromJson(history[position].history, Order::class.java)
        var prix = 0.0
        var quan = 0
        for (orders in result.orders) {
            prix += orders.dish.getPrice()*orders.quantity
            quan +=  orders.quantity
        }
        holder.date.text = history[position].date
        holder.prixTotal.text = prix.toString()
        holder.totalQuantity.text = "x" + quan.toString()
    }

    override fun getItemCount()= history.size
}