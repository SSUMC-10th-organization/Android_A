package com.example.umc_10th.data.repository

import android.content.Context
import com.example.umc_10th.Product
import com.example.umc_10th.getProductsFlow
import com.example.umc_10th.initializeProductsIfEmpty
import com.example.umc_10th.updateProductFavorite
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductLocalRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProductLocalRepository {

    override fun getProductsFlow(): Flow<List<Product>> {
        return getProductsFlow(context)
    }

    override suspend fun initializeProductsIfEmpty() {
        initializeProductsIfEmpty(context)
    }

    override suspend fun updateProductFavorite(productId: Int, isFavorite: Boolean) {
        updateProductFavorite(context, productId, isFavorite)
    }
}
