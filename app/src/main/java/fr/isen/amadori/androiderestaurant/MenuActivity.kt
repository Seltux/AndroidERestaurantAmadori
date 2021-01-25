package fr.isen.amadori.androiderestaurant

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.isen.amadori.androiderestaurant.databinding.ActivityMenuBinding

private lateinit var binding: ActivityMenuBinding
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(intent.getStringExtra(HomeActivity.CATEGORY))
    }

}