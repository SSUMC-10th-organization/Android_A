package com.example.umc_10th.data.repository

import android.content.Context
import com.example.umc_10th.data.model.ProductData
import com.example.umc_10th.data.local.ProductDataStore
import com.example.umc_10th.domain.repository.ProductRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProductRepository {

    private val productDataStore = ProductDataStore(context)

    override suspend fun initializeProductsIfEmpty() {
        productDataStore.initializeIfEmpty()
    }

    override fun getProductsFlow(): Flow<List<ProductData>> {
        return productDataStore.getProductsFlow()
    }

    override suspend fun toggleLike(productId: Int) {
        productDataStore.toggleLike(productId)
    }
}