package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import fr.isen.amadori.androiderestaurant.databinding.ActivityFinalOrderBinding
import fr.isen.amadori.androiderestaurant.oders.Order
import fr.isen.amadori.androiderestaurant.oders.OrderAdapter
import fr.isen.amadori.androiderestaurant.oders.OrderInfo
import java.io.File

private lateinit var binding: ActivityFinalOrderBinding

class FinalOrderActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idVotrePanier.text = "Votre Panier :"
        //binding.idCategoryLoaderPanier.isVisible = false
        readFileJson()
    }


    private fun readFileJson() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        if (file_name.exists()) {
            val order = gson.fromJson(file_name.readText(), Order::class.java)
            val recyclerView = binding.idListPanier
           recyclerView.adapter = OrderAdapter(order.orders.toMutableList()) {
                order.orders.remove(it)
                deleteOrder(order)
            }
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.isVisible = true
        }
    }

    fun deleteOrder(order: Order) {
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        memorySave(order, file_name)
    }

   private fun memorySave (order: Order, file_name: File) {
        updateQuantityBasket(order)
        file_name.writeText(GsonBuilder().create().toJson(order))

    }

    fun updateQuantityBasket(order: Order) {
        val quantity = order.orders.sumOf { it.quantity }
        val sharedPref = getSharedPreferences(BaseActivity.PREF_SHARED, MODE_PRIVATE)
        sharedPref.edit().putInt(BaseActivity.ORDER_COUNT, quantity).apply()
    }
}