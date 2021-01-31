package fr.isen.amadori.androiderestaurant

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import fr.isen.amadori.androiderestaurant.category.MenuActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityHomeBinding

private lateinit var binding: ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
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
            val key: String = "Entrées"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
        }
        binding.idButtonDesserts.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Desserts", Toast.LENGTH_SHORT).show()
            val menu = Intent(applicationContext, MenuActivity::class.java)
            val key: String = "Desserts"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
        }
        binding.idButtonPlats.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Plats", Toast.LENGTH_SHORT).show()
            val menu = Intent(applicationContext, MenuActivity::class.java)
            val key: String = "Plats"
            menu.putExtra(CATEGORY,key)
            startActivity(menu)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Affichage d'un log lors de "onDestroy()"
        Log.i(TAG,"onDestroy() Called")
    }

}