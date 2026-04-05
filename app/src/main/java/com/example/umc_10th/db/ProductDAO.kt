package com.example.umc_10th.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ProductDAO {
    @Insert
    fun insertProduct(product: ProductEntity)

    @Update
    fun updateProduct(product: ProductEntity)

    @Delete
    fun deleteProduct(product: ProductEntity)

    @Query("SELECT * from ProductTable")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * from ProductTable where categoryId = :categoryId")
    fun getProductsByCategoryId(categoryId: Int): List<ProductEntity>
}