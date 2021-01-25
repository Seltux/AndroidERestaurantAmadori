package fr.isen.amadori.androiderestaurant

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.amadori.androiderestaurant.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toast & Redirection
        binding.idButtonEntree.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Entrées", Toast.LENGTH_SHORT).show()
        }
        binding.idButtonDesserts.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Desserts", Toast.LENGTH_SHORT).show()
        }
        binding.idButtonPlats.setOnClickListener {
            Toast.makeText(this, "Vous avez choisis nos Plats", Toast.LENGTH_SHORT).show()
            val plats: Intent = Intent(applicationContext, PlatsActivity::class.java)
            startActivity(plats)
        }

    }


}