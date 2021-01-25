package fr.isen.amadori.androiderestaurant


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import fr.isen.amadori.androiderestaurant.databinding.ActivityMenuBinding

private lateinit var binding: ActivityMenuBinding
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idTitreMenu.text = intent.getStringExtra(HomeActivity.CATEGORY)
        binding.idListMenu.layoutManager = LinearLayoutManager(this)
        val menu_name = resources.getStringArray(R.array.menu_array).toList()
        binding.idListMenu.adapter = CategoriesAdapter(menu_name)
    }
}