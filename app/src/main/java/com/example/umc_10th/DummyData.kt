package com.example.umc_10th

object DummyData {
    fun getProducts(): MutableList<Product> = mutableListOf(
        Product(
            1,
            "Nike Everyday Plus Cushioned",
            "Training Ankle Socks (6 Pairs)",
            "US$10", "5 Colours",
            false,
            R.drawable.img_product_1,
            true
        ),
        Product(
            2,
            "Nike Elite Crew",
            "Basketball Socks",
            "US$16",
            "7 Colours",
            false,
            R.drawable.img_product_2),
        Product(
            3,
            "Nike Air Force 1 '07",
            "Women's Shoes",
            "US$115",
            "5 Colours",
            true,
            R.drawable.img_product_3),
        Product(
            4,
            "Jordan ENike Air Force 1 '07ssentials",
            "Men's Shoes",
            "US$115",
            "2 Colours",
            true,
            R.drawable.img_product_4),
        Product(
            5,
            "Air Jordan XXXVI",
            "Basketball Shoes",
            "US$185",
            "",
            false,
            R.drawable.img_product_5),
        Product(
            6,
            "Air Jordan 1 Mid",
            "Shoes",
            "US$125",
            "",
            false,
            R.drawable.img_product_6)
    )
}