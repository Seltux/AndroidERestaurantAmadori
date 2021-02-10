package fr.isen.amadori.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Dish(
    @SerializedName("name_fr") val title: String,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("images") val images: List<String>,
    @SerializedName("prices") val prix: List<Price>
) : Serializable {
    fun getPrice() = prix[0].price.toDouble()
    fun getFormattedPrice() = prix[0].price + "€"
    fun getFirstImage() = if(images.isNotEmpty() && images[0].isNotEmpty()){
        images[0]
    }else{
        null
    }
}