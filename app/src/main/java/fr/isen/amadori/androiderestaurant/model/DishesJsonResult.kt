package fr.isen.amadori.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DishesJsonResult(@SerializedName("data") val data: List<Category>):Serializable