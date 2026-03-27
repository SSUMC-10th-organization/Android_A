package com.example.umc_10th

object ProductSampleRepository {

    val productList = mutableListOf(
        ProductData(
            imageResId = R.drawable.ic_launcher_background,
            name = "Nike Everyday Plus Cushioned",
            category = "Training Ankle Socks (6 Pairs)",
            colorCount = 5,
            price = "US$10",
            isLiked = false,
            isBestSeller = false
        ),
        ProductData(
            imageResId = R.drawable.ic_launcher_background,
            name = "Nike Air Force 1 '07",
            category = "Women's Shoes",
            colorCount = 2,
            price = "US$115",
            isLiked = false,
            isBestSeller = true
        ),
        ProductData(
            imageResId = R.drawable.ic_launcher_background,
            name = "Nike Dunk Low",
            category = "Men's Shoes",
            colorCount = 3,
            price = "US$120",
            isLiked = false,
            isBestSeller = false
        ),
        ProductData(
            imageResId = R.drawable.ic_launcher_background,
            name = "Nike Motiva",
            category = "Women's Walking Shoes",
            colorCount = 4,
            price = "US$130",
            isLiked = false,
            isBestSeller = true
        )
    )
}