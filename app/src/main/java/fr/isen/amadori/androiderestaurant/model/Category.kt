package fr.isen.amadori.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(@SerializedName("name_fr") val name_category: String, @SerializedName("items") val dishes: List<Dish>):Serializable