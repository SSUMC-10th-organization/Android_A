package com.example.umc_10th

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "product_store")

class ProductDataStore(private val context: Context) {

    companion object {
        private val PRODUCT_LIST_KEY = stringPreferencesKey("product_list")
    }

    fun getProductsFlow(): Flow<List<ProductData>> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val jsonString = preferences[PRODUCT_LIST_KEY]
                if (jsonString.isNullOrEmpty()) {
                    emptyList()
                } else {
                    ProductJsonUtil.jsonToProductList(jsonString)
                }
            }
    }

    suspend fun saveProducts(productList: List<ProductData>) {
        context.dataStore.edit { preferences ->
            preferences[PRODUCT_LIST_KEY] =
                ProductJsonUtil.productListToJson(productList)
        }
    }

    suspend fun initializeIfEmpty() {
        val currentList = getProductsFlow().first()
        if (currentList.isEmpty()) {
            saveProducts(ProductSampleRepository.productList)
        }
    }

    suspend fun toggleLike(productId: Int) {
        val currentList = getProductsFlow().first().toMutableList()
        val index = currentList.indexOfFirst { it.id == productId }

        if (index != -1) {
            val oldProduct = currentList[index]
            currentList[index] = oldProduct.copy(isLiked = !oldProduct.isLiked)
            saveProducts(currentList)
        }
    }
}