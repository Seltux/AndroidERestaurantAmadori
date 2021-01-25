package fr.isen.amadori.androiderestaurant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.isen.amadori.androiderestaurant.databinding.ActivityPlatsBinding

private lateinit var binding: ActivityPlatsBinding
class PlatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}