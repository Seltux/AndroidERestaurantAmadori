package fr.isen.amadori.androiderestaurant.oders

import com.google.gson.annotations.SerializedName
import fr.isen.amadori.androiderestaurant.model.Dish

data class OrderInfo(@SerializedName("dish") var dish: Dish, @SerializedName("quantity") var quantity: Int)