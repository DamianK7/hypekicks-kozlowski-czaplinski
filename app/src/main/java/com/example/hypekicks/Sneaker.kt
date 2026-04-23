package com.example.hypekicks

import java.io.Serializable

data class Sneaker(
    var id: String = "",
    val brand: String = "",
    val modelName: String = "",
    val releaseYear: Int = 0,
    val resellPrice: Int = 0,
    val imageUrl: String = ""
) : Serializable