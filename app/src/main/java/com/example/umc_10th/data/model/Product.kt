package com.example.umc_10th.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "product_table")

data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageRes: Int,
    val name: String,
    val price: String
)