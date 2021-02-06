package fr.isen.amadori.androiderestaurant.profile

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryList(@SerializedName("data") val list: List<History>): Serializable