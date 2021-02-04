    package fr.isen.amadori.androiderestaurant.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.amadori.androiderestaurant.BaseActivity
import fr.isen.amadori.androiderestaurant.DetailsActivity
import fr.isen.amadori.androiderestaurant.HomeActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityMenuBinding
import fr.isen.amadori.androiderestaurant.model.Dish
import fr.isen.amadori.androiderestaurant.model.DishesJsonResult
import org.json.JSONException
import org.json.JSONObject


    private lateinit var binding: ActivityMenuBinding
class MenuActivity  : BaseActivity()  {
    companion object{
        const val NOMREPASDETAILS = "nom_repas_details"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.setRefreshing(false)
        }
        binding.idTitreMenu.text = intent.getStringExtra(HomeActivity.CATEGORY)

        volleyPost(intent.getStringExtra("Category") ?: "")
    }


    fun volleyPost(category: String) {
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)
        val postData = JSONObject()
        try {
            postData.put("id_shop", "1")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, postUrl, postData,
            {
                //LA
                val gson: DishesJsonResult =
                    Gson().fromJson(it.toString(), DishesJsonResult::class.java)
                gson.data.firstOrNull { it.name_category == category }?.dishes?.let { dishes ->
                    displayCategories(dishes)
                } ?: run {
                    Log.d("HomeActivity", it.toString())
                }
            },
            {
                Log.e("HomeActivity", it.toString())
            })
        requestQueue.add(jsonObjectRequest)
    }
    private fun displayCategories(menu_name: List<Dish>) {
        binding.idCategoryLoader.visibility = View.GONE
        binding.idListMenu.visibility = View.VISIBLE
        binding.idListMenu.layoutManager = LinearLayoutManager(this)
        binding.idListMenu.adapter = CategoriesAdapter(menu_name) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(NOMREPASDETAILS, it)
            startActivity(intent)
        }
    }
}
