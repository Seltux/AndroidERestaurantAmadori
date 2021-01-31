package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.amadori.androiderestaurant.fragments.DetailsCarouselAdapter
import fr.isen.amadori.androiderestaurant.model.Dish
import fr.isen.amadori.androiderestaurant.oders.Order
import fr.isen.amadori.androiderestaurant.oders.OrderInfo
import org.json.JSONObject
import java.io.File


private lateinit var binding: ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dishInfo: Dish = intent.getSerializableExtra(MenuActivity.NOMREPASDETAILS) as Dish
        binding.idIngredientsRepasDetails.text = dishInfo.getIngredients()

        /*if (dishInfo != null) {
            //Picasso.get().load(dishInfo.getFirstImage()).into(binding.idImageRepasDetails)
            val sampleImages = intArrayOf(
                R.drawable.guide_michelin,
                R.drawable.jokes_about_italians,
                R.drawable.kebab_boeuf,
                R.drawable.logo_restaurant,
                R.drawable.pizza
            )
            val imageListener =
                ImageListener { position, imageView ->
                    Picasso.get().load(dishInfo.getFirstImage()).into(
                        imageView
                    )
                }
           // binding.carouselView.pageCount = sampleImages.size
            //binding.carouselView.setImageListener(imageListener)
        }*/
        binding.idNomRepasDetails.text = dishInfo.title
        binding.idPriceRepasDetails.text = "Total :" + dishInfo.getFormattedPrice()

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

    fun jsonOrderFile(dishInfo: Dish, quantity: Int) {
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val orderInfo = OrderInfo(dishInfo, quantity)
        if (file_name.exists()) {
            val json = gson.fromJson(file_name.readText(), Order::class.java)
            json.orders.add(orderInfo)
            file_name.writeText(gson.toJson(json))
        }else{
            val jsonObject =  gson.toJson(Order(mutableListOf(orderInfo)))
            file_name.writeText(jsonObject)
        }

    }
}
