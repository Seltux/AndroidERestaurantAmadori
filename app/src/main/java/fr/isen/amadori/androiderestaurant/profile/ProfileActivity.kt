package fr.isen.amadori.androiderestaurant.profile

import android.os.Bundle
import fr.isen.amadori.androiderestaurant.BaseActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityProfileBinding

private lateinit var binding: ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}