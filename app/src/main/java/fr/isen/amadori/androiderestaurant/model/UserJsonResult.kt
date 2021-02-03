package fr.isen.amadori.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserJsonResult (@SerializedName("data") val data: User):
    Serializable