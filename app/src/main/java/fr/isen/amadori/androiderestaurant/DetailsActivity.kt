package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.amadori.androiderestaurant.fragments.DetailsCarouselAdapter
import fr.isen.amadori.androiderestaurant.model.Dish
import fr.isen.amadori.androiderestaurant.order.Order
import fr.isen.amadori.androiderestaurant.order.OrderInfo
import fr.isen.amadori.androiderestaurant.security.decrypt
import fr.isen.amadori.androiderestaurant.security.encrypt
import java.io.File
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets


private lateinit var binding: ActivityDetailsBinding

class DetailsActivity : BaseActivity() {

    var quantity = 1

    @SuppressLint("SetTextI18n")
    fun setText() {
        binding.idQuantity.setText(quantity.toString() + "")

    }

    @SuppressLint("SetTextI18n")
    fun setPrice(dishInfo: Dish) {
        val price_quantity: Double = quantity.toDouble() * dishInfo.getPrice()
        binding.idPriceRepasDetails.text = "Total :" + price_quantity.toString() + "€"
    }

    override fun onResume() {
        invalidateOptionsMenu()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dishInfo: Dish = intent.getSerializableExtra(MenuActivity.NOMREPASDETAILS) as Dish
        getInfoAboutDish(binding, dishInfo)
        updateQuantity(binding, dishInfo)
        displaySnackBarAndJson(binding, dishInfo)
    }

    fun displaySnackBarAndJson(binding: ActivityDetailsBinding, dishInfo: Dish) {
        binding.idPagerVeiw.adapter = DetailsCarouselAdapter(this, dishInfo.images)
        binding.idPriceRepasDetails.setOnClickListener {
            Toast.makeText(
                this,
                quantity.toString() + "x " + dishInfo.title + " Ajouté au panier",
                Toast.LENGTH_SHORT
            ).show()
            jsonOrderFile(dishInfo, quantity)

        }
    }

    fun updateQuantity(binding: ActivityDetailsBinding, dishInfo: Dish) {
        binding.idFloatButtonPlus.setOnClickListener {
            quantity++
            setText()
            setPrice(dishInfo)
        }
        binding.idFloatButtonMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
            }
            setText()
            setPrice(dishInfo)
        }
    }

    @SuppressLint("SetTextI18n")
    fun getInfoAboutDish(binding: ActivityDetailsBinding, dishInfo: Dish) {
        val temp = dishInfo.ingredients
        val prettyDescription = StringBuilder("")
        for(description in temp){
            prettyDescription.append("\uD83D\uDFE2 " + description.ingredient_name + "\n")
        }
        binding.idIngredientsRepasDetails.text = prettyDescription.toString()
        binding.idNomRepasDetails.text = dishInfo.title
        binding.idPriceRepasDetails.text = "Total :" + dishInfo.getFormattedPrice()
    }


    fun jsonOrderFile(dishInfo: Dish, quantity: Int) {
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val orderInfo = OrderInfo(dishInfo, quantity)
        if (file_name.exists()) {
            val json = gson.fromJson(file_name.readText(), Order::class.java)
            json.orders.firstOrNull { it.dish == dishInfo }?.let {
                it.quantity.apply { it.quantity += quantity }
            } ?: run {
                json.orders.add(orderInfo)
            }
            updateQuantityBasket(json)
            file_name.writeText(gson.toJson(json))
        } else {
            val order = Order(mutableListOf(OrderInfo(dishInfo,quantity)))
            updateQuantityBasket(order)
            file_name.writeText(gson.toJson(Order(mutableListOf(orderInfo))))
        }
    }
}

