package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.amadori.androiderestaurant.fragments.DetailsCarouselAdapter
import fr.isen.amadori.androiderestaurant.model.Dish
import fr.isen.amadori.androiderestaurant.oders.Order
import fr.isen.amadori.androiderestaurant.oders.OrderInfo
import java.io.File


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
            Snackbar.make(
                binding.root,
                quantity.toString() + "x " + dishInfo.title + " Ajouté au panier",
                Snackbar.LENGTH_SHORT
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
        binding.idIngredientsRepasDetails.text = dishInfo.getIngredients()
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

