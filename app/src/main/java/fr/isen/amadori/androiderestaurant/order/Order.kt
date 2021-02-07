package fr.isen.amadori.androiderestaurant.order

import com.google.gson.annotations.SerializedName

data class Order (@SerializedName("orders")val orders: MutableList<OrderInfo>)