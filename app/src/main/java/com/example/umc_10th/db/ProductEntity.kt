package com.example.umc_10th.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ProductTable")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val imageResource: Int,
    val price: Int,
    val liked: Boolean,
    val categoryId:Int
)