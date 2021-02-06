package fr.isen.amadori.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import fr.isen.amadori.androiderestaurant.oders.Order

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        invalidateOptionsMenu()
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.badge)
        MenuItemCompat.setActionView(menuItem, R.layout.header_layout)
        val order = menuItem.actionView
        val sharedPref = getSharedPreferences(PREF_SHARED, MODE_PRIVATE)
        val totalQuantity = sharedPref.getInt(ORDER_COUNT,0)
        order?.findViewById<TextView>(R.id.idDishCount)?.text = totalQuantity.toString()

        order?.findViewById<ImageView>(R.id.idPizzaCart)?.setOnClickListener{
            val intent = Intent(this, FinalOrderActivity::class.java)
            startActivity(intent)
        }

        if(sharedPref.contains(SignUpActivity.ID_USER)){
            
        }
        return true
    }

    fun updateQuantityBasket(order: Order) {
        val quantity = order.orders.sumOf { it.quantity }
        val sharedPref = getSharedPreferences(PREF_SHARED, MODE_PRIVATE)
        sharedPref.edit().putInt(ORDER_COUNT,quantity).apply()
        invalidateOptionsMenu()
    }


    companion object{
        const val PREF_SHARED = "shared_pref"
        const val ORDER_COUNT = "order_count"
    }

}