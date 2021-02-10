package fr.isen.amadori.androiderestaurant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityDeliveryBinding

private lateinit var binding: ActivityDeliveryBinding

class DeliveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCreate()
    }

    private fun initCreate(){
        binding.animationView.playAnimation()
        binding.idDeliveryReturnToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}