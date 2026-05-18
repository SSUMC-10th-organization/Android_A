package com.example.umc_10th.data.repository

import android.content.Context
import com.example.umc_10th.data.ProductDataStore
import com.example.umc_10th.ui.ProductData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getHomeProducts(): Flow<List<ProductData>> =
        ProductDataStore.getHomeProducts(context)

    fun getPurchaseProducts(): Flow<List<ProductData>> =
        ProductDataStore.getPurchaseProducts(context)

    suspend fun saveHomeProducts(products: List<ProductData>) =
        ProductDataStore.saveHomeProducts(context, products)

    suspend fun savePurchaseProducts(products: List<ProductData>) =
        ProductDataStore.savePurchaseProducts(context, products)

    suspend fun updateProductLiked(index: Int, isLiked: Boolean) =
        ProductDataStore.updateProductLiked(context, index, isLiked)
}
