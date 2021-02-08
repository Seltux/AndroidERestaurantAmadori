package fr.isen.amadori.androiderestaurant.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.amadori.androiderestaurant.BaseActivity
import fr.isen.amadori.androiderestaurant.HomeActivity
import fr.isen.amadori.androiderestaurant.SignUpActivity
import fr.isen.amadori.androiderestaurant.databinding.ActivityProfileBinding
import org.json.JSONException
import org.json.JSONObject

private lateinit var binding: ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(PREF_SHARED, MODE_PRIVATE)
        initCreate(sharedPref)
        getOrdersHistory(sharedPref)

    }

    override fun onResume() {
        invalidateOptionsMenu()
        super.onResume()
    }

    private fun initCreate(sharedPref: SharedPreferences){
        binding.idLogoutProfile.setOnClickListener {
            sharedPref.edit().remove(SignUpActivity.ID_USER).apply();
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getOrdersHistory(sharedPref: SharedPreferences) {
        val url = "http://test.api.catering.bluecodegames.com/listorders"
        val volleyRequest = Volley.newRequestQueue(this)
        val postData = JSONObject()

        try {
            postData.put("id_shop", "1")
            postData.put("id_user", sharedPref.getInt(SignUpActivity.ID_USER, 0))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObject = JsonObjectRequest(
            Request.Method.POST,
            url,
            postData,
            { response ->
                val gson = Gson().fromJson(response.toString(), HistoryList::class.java)
                gson.list?.let {
                    binding.idOrderHistory.adapter = ProfileAdapter(it, binding.idNomProfile)
                    binding.idCategoryLoaderHistory.isVisible = false
                    binding.idOrderHistory.layoutManager =LinearLayoutManager(this)
                    binding.idOrderHistory.isVisible = true

                }

            },
            {
                Log.e("ProfileActivity", it.toString())
            })
        volleyRequest.add(jsonObject)
    }
}
