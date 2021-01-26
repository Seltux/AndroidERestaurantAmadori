package fr.isen.amadori.androiderestaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.idNomRepasDetails.text = intent.getStringExtra(MenuActivity.NOMREPASDETAILS)
        if (dishInfo != null) {
            binding.idIngredientsRepasDetails.text = dishInfo.getIngredients().toString()
        }
        if (dishInfo != null) {
            Picasso.get().load(dishInfo.getFirstImage()).into(binding.idImageRepasDetails)
        }


    }
}