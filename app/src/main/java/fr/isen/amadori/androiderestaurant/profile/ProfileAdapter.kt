package fr.isen.amadori.androiderestaurant.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.amadori.androiderestaurant.databinding.HistoryOrderBinding
import fr.isen.amadori.androiderestaurant.order.Order
import fr.isen.amadori.androiderestaurant.order.OrderInfo

class ProfileAdapter(
    private val history: List<History>
): RecyclerView.Adapter<ProfileAdapter.ProfileHolder>() {

    class ProfileHolder(binding: HistoryOrderBinding): RecyclerView.ViewHolder(binding.root){
        val layout = binding.root
        val prixTotal = binding.idPrixTotalItem
        val totalQuantity = binding.idNombreItem
        val date = binding.idDateHistory
        val recap = binding.idAllDishes
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
        val temp: MutableList<OrderInfo>? = result.orders
        val prettyDescription = StringBuilder("")
        if (temp != null) {
            for (orders in temp) {
                prettyDescription.append("\uD83D\uDFE2 " + orders.dish.title + "\n")
                prix += orders.dish.getPrice()*orders.quantity
                quan +=  orders.quantity
            }
        }
        holder.recap.text = prettyDescription.toString()
        holder.date.text = history[position].date
        holder.prixTotal.text = "$prix €"
        holder.totalQuantity.text = "x" + quan.toString()
    }

    override fun getItemCount()= history.size
}