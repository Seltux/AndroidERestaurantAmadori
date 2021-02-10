package fr.isen.amadori.androiderestaurant.launch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.amadori.androiderestaurant.HomeActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityWelcomeBinding


private lateinit var binding: ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.idBienvenu.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(10000).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    finish()
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        binding.serveurAnimation.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(10000).setListener(null)
        }
        binding.imageView.apply {
            alpha = 0f
            animate().alpha(1f).setDuration(10000).setListener(null)
        }
    }
}