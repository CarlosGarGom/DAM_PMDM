package com.example.memory

data class Card(
    val id: Int,
    val imageResId: Int, // Reemplazamos el número por el ID del recurso de imagen
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)