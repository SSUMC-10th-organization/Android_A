package com.example.umc_10th.data

data class PurchaseProduct(
    val imageRes: Int,
    val name: String? = null,
    val explain: String? = null,
    val price: String? = null,

    var isFavorite: Boolean = false
)