package fr.isen.amadori.androiderestaurant

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.squareup.picasso.Picasso
import fr.isen.amadori.androiderestaurant.category.CategoriesAdapter
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.amadori.androiderestaurant.model.Dish
import java.io.Serializable

private lateinit var binding:ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dishInfo: Dish? = intent.getSerializableExtra(MenuActivity.NOMREPASDETAILS) as? Dish
        if (dishInfo != null) {
            binding.idIngredientsRepasDetails.text = dishInfo.getIngredients()
        }
        if (dishInfo != null) {
            Picasso.get().load(dishInfo.getFirstImage()).into(binding.idImageRepasDetails)
        }
        if (dishInfo != null) {
            binding.idNomRepasDetails.text = dishInfo.title
        }
        if (dishInfo != null) {
            binding.idPriceRepasDetails.text = dishInfo.getFormattedPrice()
        }
    }
}