package com.example.umc_10th.domain.repository

import com.example.umc_10th.data.model.ProductData
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun initializeProductsIfEmpty()
    fun getProductsFlow(): Flow<List<ProductData>>
    suspend fun toggleLike(productId: Int)
}