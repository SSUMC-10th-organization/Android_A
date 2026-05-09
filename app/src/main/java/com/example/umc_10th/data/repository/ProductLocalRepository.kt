package com.example.umc_10th.data.repository

import com.example.umc_10th.Product
import kotlinx.coroutines.flow.Flow

interface ProductLocalRepository {
    fun getProductsFlow(): Flow<List<Product>>
    suspend fun initializeProductsIfEmpty()
    suspend fun updateProductFavorite(productId: Int, isFavorite: Boolean)
}
