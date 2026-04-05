package com.example.umc_10th


data class ProductData (
    val id: Int,
    val imageResId: Int,
    val name: String,
    val category: String,
    val colorCount: Int,
    val price: String,
    var isLiked: Boolean = false,
    val isBestSeller: Boolean = false
)
