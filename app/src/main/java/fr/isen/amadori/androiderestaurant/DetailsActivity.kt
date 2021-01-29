package fr.isen.amadori.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.amadori.androiderestaurant.model.Dish


private lateinit var binding:ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    var quantity = 0

    fun setText() {
        binding.idQuantity.setText(quantity.toString() + "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dishInfo: Dish? = intent.getSerializableExtra(MenuActivity.NOMREPASDETAILS) as? Dish
        if (dishInfo != null) {
            binding.idIngredientsRepasDetails.text = dishInfo.getIngredients()
        }
        if (dishInfo != null) {
            //Picasso.get().load(dishInfo.getFirstImage()).into(binding.idImageRepasDetails)
            val sampleImages = intArrayOf(
                R.drawable.guide_michelin,
                R.drawable.jokes_about_italians,
                R.drawable.kebab_boeuf,
                R.drawable.logo_restaurant,
                R.drawable.pizza
            )
            val imageListener =
                ImageListener { position, imageView -> Picasso.get().load(dishInfo.getFirstImage()).into(
                    imageView
                ) }
            binding.carouselView.pageCount = sampleImages.size
            binding.carouselView.setImageListener(imageListener)
        }
        if (dishInfo != null) {
            binding.idNomRepasDetails.text = dishInfo.title
        }
        if (dishInfo != null) {
            binding.idPriceRepasDetails.text = dishInfo.getFormattedPrice()
        }
        binding.idFloatButtonPlus.setOnClickListener {
            quantity++
            setText()
        }
        binding.idFloatButtonMinus.setOnClickListener {
            if(quantity>1) {
                quantity--
            }
            setText()
        }

    }
}