package fr.isen.amadori.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserJsonResult (@SerializedName("users") val users: User, @SerializedName("success_requet") val success_request: Int):
    Serializable