package fr.isen.amadori.androiderestaurant.model

import java.io.Serializable

data class User(var firstname: String, var lastname: String, var addresse: String, var email: String, var password: String):Serializable