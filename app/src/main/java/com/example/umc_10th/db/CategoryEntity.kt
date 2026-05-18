package com.example.umc_10th.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("CategoryTable")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)