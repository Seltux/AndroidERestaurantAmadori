package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.amadori.androiderestaurant.databinding.ActivityFinalOrderBinding
import fr.isen.amadori.androiderestaurant.oders.Order
import fr.isen.amadori.androiderestaurant.oders.OrderAdapter
import org.json.JSONException
import org.json.JSONObject
import java.io.File


private lateinit var binding: ActivityFinalOrderBinding

class FinalOrderActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idVotrePanier.text = "Votre Panier :"
        binding.idCategoryLoaderPanier.isVisible = false
        binding.animationView.isVisible = false
        binding.idButtonPayOrder.setOnClickListener {
            if(getSharedPreferences(BaseActivity.PREF_SHARED, MODE_PRIVATE).contains(SignUpActivity.ID_USER)) {
               animationThread()
            }else {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
        readFileJsonThread()
    }

    @SuppressLint("ResourceType")
    private fun deliveryWaitingAnimation(){
        binding.animationView.playAnimation()
    }

    private fun animationThread(){
        object : Thread() {
            override fun run() {
                try {
                    // code runs in a thread
                    runOnUiThread {  sendFinalOrder()
                        deliveryWaitingAnimation() }
                } catch (ex: Exception) {
                    Log.i("---", "Exception in thread")
                }
            }
        }.start()
    }

    private fun readFileJsonThread(){
        object : Thread() {
            override fun run() {
                try {
                    // code runs in a thread
                    runOnUiThread {  readFileJson() }
                } catch (ex: Exception) {
                    Log.i("---", "Exception in thread")
                }
            }
        }.start()
    }

    private fun readFileJson() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        if (file_name.exists()) {
            val order = gson.fromJson(file_name.readText(), Order::class.java)
            val recyclerView = binding.idListPanier
           recyclerView.adapter = OrderAdapter(order.orders, binding.idButtonPayOrder) {
                order.orders.remove(it)
                deleteOrder(order)
            }
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.isVisible = true
        }
    }

    fun deleteOrder(order: Order) {
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        memorySave(order, file_name)
    }

   private fun memorySave(order: Order, file_name: File) {
        updateQuantityBasket(order)
        file_name.writeText(GsonBuilder().create().toJson(order))

    }

    fun updateQuantityBasket(order: Order) {
        val quantity = order.orders.sumOf { it.quantity }
        val sharedPref = getSharedPreferences(BaseActivity.PREF_SHARED, MODE_PRIVATE)
        sharedPref.edit().putInt(BaseActivity.ORDER_COUNT, quantity).apply()
    }

    private fun sendFinalOrder(){
        val url = "http://test.api.catering.bluecodegames.com/user/order"
        val request = Volley.newRequestQueue(this)
        val postData = JSONObject()
        var order : Order? = null
        val id_user = getSharedPreferences(BaseActivity.PREF_SHARED, MODE_PRIVATE).getInt(
            SignUpActivity.ID_USER,
            0
        )
        val file_name = File(cacheDir.absolutePath + "Basket.json")
        if (file_name.exists()) {
             order = GsonBuilder().setPrettyPrinting().create().fromJson(
                 file_name.readText(),
                 Order::class.java
             )
        }
        try {
            postData.put("id_shop", "1")
            postData.put("id_user", id_user)
            postData.put("msg", GsonBuilder().create().toJson(order))
        }catch (e: JSONException){
            e.printStackTrace()
        }
        val jsonObject = JsonObjectRequest(Request.Method.POST, url, postData, { response ->
            binding.animationView.visibility = View.GONE
            Snackbar.make(
                binding.root,
                "Commande passée :)",
                Snackbar.LENGTH_SHORT
            ).show()
            val intent = Intent(this, DeliveryActivity::class.java)
            startActivity(intent)
        },
            {
                Snackbar.make(
                    binding.root,
                    "Quelque chose s'est mal passé :(, veuillez réassayer.",
                    Snackbar.LENGTH_SHORT
                ).show()
                Log.e("error signu form", it.toString())
            })
        request.add(jsonObject)
    }
}