package fr.isen.amadori.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : BaseActivity() {
    companion object{
        const val TAG = "HomeActivity"
        const val CATEGORY = "Category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Affichage d'un log lors de onCreate()
        Log.i(TAG, "onCreate() Called")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toast & Redirection
        binding.idButtonEntree.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Entrées", Toast.LENGTH_SHORT).show()
            val menu = Intent(applicationContext, MenuActivity::class.java)
            val key = "Entrées"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
        }
        binding.idButtonDesserts.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Desserts", Toast.LENGTH_SHORT).show()
            val menu = Intent(applicationContext, MenuActivity::class.java)
            val key = "Desserts"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
        }
        binding.idButtonPlats.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Plats", Toast.LENGTH_SHORT).show()
            val menu = Intent(applicationContext, MenuActivity::class.java)
            val key = "Plats"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
            invalidateOptionsMenu()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Affichage d'un log lors de "onDestroy()"
        Log.i(TAG,"onDestroy() Called")
    }

}