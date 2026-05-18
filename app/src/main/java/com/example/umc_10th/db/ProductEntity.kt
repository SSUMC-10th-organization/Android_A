package com.example.umc_10th.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ProductTable")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 기본값 0
    val name: String,
    val description: String = "", // 기본값 빈 문자열
    val imageResource: Int = 0,
    val price: Int = 0,
    val liked: Boolean = false,
    val categoryId: Int = 0
)