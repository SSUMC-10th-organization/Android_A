package com.example.umc_10th
import java.io.Serializable

data class DetailProductData(
    val category: String,
    val name: String,
    val price: String,
    val description: String,
    val imageRes: Int, // R.drawable.xxx 형태의 ID
    val styleCode: String,
    val color: String
) : Serializable