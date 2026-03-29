package com.example.umc_10th

import androidx.annotation.DrawableRes

data class Product (
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val colorInfo: String = "",
    val isBestSeller: Boolean = false,
    @DrawableRes
    val imageRes: Int,
    var isFavorite: Boolean = false
)