package bydartlen.thecatapp.data.network

import bydartlen.thecatapp.catlist.adapter.HEART
import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("breeds") val breeds: List<Breed> = listOf(),
    @SerializedName("height") val height: Int = 0,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int = 0,
    var heart: HEART = HEART.UNLIKE
)