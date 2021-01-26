package fr.isen.amadori.androiderestaurant.category


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.amadori.androiderestaurant.DetailsActivity
import fr.isen.amadori.androiderestaurant.HomeActivity
import fr.isen.amadori.androiderestaurant.R
import fr.isen.amadori.androiderestaurant.databinding.ActivityMenuBinding
import org.json.JSONException
import org.json.JSONObject


private lateinit var binding: ActivityMenuBinding
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idTitreMenu.text = intent.getStringExtra(HomeActivity.CATEGORY)
        binding.idListMenu.layoutManager = LinearLayoutManager(this)
        val menu_name = resources.getStringArray(R.array.menu_array).toList()
        binding.idListMenu.adapter = CategoriesAdapter(menu_name){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("Category", it)
            startActivity(intent)
        }
        //load data
        volleyPost()
    }

    fun volleyPost() {
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
                Log.d("HomeActivity", it.toString())
            },
             {
                Log.e("HomeActivity", it.toString())
            })
        requestQueue.add(jsonObjectRequest)
    }
}