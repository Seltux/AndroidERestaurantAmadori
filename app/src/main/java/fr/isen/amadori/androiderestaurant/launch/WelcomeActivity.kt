package fr.isen.amadori.androiderestaurant.launch

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.isen.amadori.androiderestaurant.HomeActivity
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.ActivityWelcomeBinding


private lateinit var binding: ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBienvenu.apply {
            alpha = 0f
            animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    finish()
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        binding.serveurAnimation.apply {
            alpha = 0f
            animate().alpha(1f).setListener(null)
        }
        binding.imageView.apply {
            alpha = 0f
            animate().alpha(1f).setListener(null)
        }
    }
}