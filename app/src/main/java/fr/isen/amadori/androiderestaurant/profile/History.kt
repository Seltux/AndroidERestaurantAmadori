package fr.isen.amadori.androiderestaurant.profile

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class History(@SerializedName("message") val history: String, @SerializedName("create_date") val date: String, @SerializedName("sender") val sender: String):Serializable
