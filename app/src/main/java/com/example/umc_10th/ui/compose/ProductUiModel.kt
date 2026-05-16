package com.example.umc_10th.ui.compose

import androidx.annotation.DrawableRes
import com.example.umc_10th.R

data class ProductUiModel(
    val id: Int,
    val name: String,
    val category: String,
    val colorCount: String,
    val price: String,
    @DrawableRes val imageRes: Int,
    val isLiked: Boolean = false
)

fun getInitialProducts(): List<ProductUiModel> {
    return listOf(
        ProductUiModel(
            id = 1,
            name = "Nike Everyday Plus Cushioned",
            category = "Training Ankle Socks (6 Pairs)",
            colorCount = "5 Colours",
            price = "US$10",
            imageRes = R.drawable.ic_purchase_item1
        ),
        ProductUiModel(
            id = 2,
            name = "Air Jordan XXXVI",
            category = "Basketball Shoes",
            colorCount = "3 Colours",
            price = "US$185",
            imageRes = R.drawable.ic_purchase_item2
        ),
        ProductUiModel(
            id = 3,
            name = "Nike Sportswear Club",
            category = "Men's T-Shirt",
            colorCount = "4 Colours",
            price = "US$35",
            imageRes = R.drawable.ic_purchase_item3
        ),
        ProductUiModel(
            id = 4,
            name = "Nike Dri-FIT",
            category = "Women's Training Top",
            colorCount = "2 Colours",
            price = "US$42",
            imageRes = R.drawable.ic_purchase_item4
        ),
        ProductUiModel(
            id = 5,
            name = "Air Jordans XXXVI",
            category = "Basketball Shoes",
            colorCount = "3 Colours",
            price = "US$185",
            imageRes = R.drawable.ic_purchase_item2
        ),
        ProductUiModel(
            id = 6,
            name = "Air Jordanss XXXVI",
            category = "Basketball Shoes",
            colorCount = "3 Colours",
            price = "US$185",
            imageRes = R.drawable.ic_purchase_item2
        )
    )
}