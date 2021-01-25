package fr.isen.amadori.androiderestaurant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.isen.amadori.androiderestaurant.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}