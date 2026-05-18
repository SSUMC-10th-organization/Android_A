package com.example.umc_10th.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.umc_10th.ui.ProductData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.productDataStore: DataStore<Preferences> by preferencesDataStore(name = "product_prefs")

object ProductDataStore {
    private val gson = Gson()
    private val HOME_PRODUCTS_KEY = stringPreferencesKey("home_products")
    private val PURCHASE_PRODUCTS_KEY = stringPreferencesKey("purchase_products")

    fun getHomeProducts(context: Context): Flow<List<ProductData>> =
        context.productDataStore.data.map { prefs ->
            val json = prefs[HOME_PRODUCTS_KEY] ?: return@map emptyList()
            gson.fromJson(json, object : TypeToken<List<ProductData>>() {}.type) ?: emptyList()
        }

    fun getPurchaseProducts(context: Context): Flow<List<ProductData>> =
        context.productDataStore.data.map { prefs ->
            val json = prefs[PURCHASE_PRODUCTS_KEY] ?: return@map emptyList()
            gson.fromJson(json, object : TypeToken<List<ProductData>>() {}.type) ?: emptyList()
        }

    suspend fun saveHomeProducts(context: Context, products: List<ProductData>) {
        context.productDataStore.edit { prefs ->
            prefs[HOME_PRODUCTS_KEY] = gson.toJson(products)
        }
    }

    suspend fun savePurchaseProducts(context: Context, products: List<ProductData>) {
        context.productDataStore.edit { prefs ->
            prefs[PURCHASE_PRODUCTS_KEY] = gson.toJson(products)
        }
    }

    suspend fun updateProductLiked(context: Context, index: Int, isLiked: Boolean) {
        context.productDataStore.edit { prefs ->
            val json = prefs[PURCHASE_PRODUCTS_KEY] ?: return@edit
            val type = object : TypeToken<List<ProductData>>() {}.type
            val products: MutableList<ProductData> = gson.fromJson(json, type) ?: return@edit
            if (index in products.indices) {
                products[index] = products[index].copy(isLiked = isLiked)
                prefs[PURCHASE_PRODUCTS_KEY] = gson.toJson(products)
            }
        }
    }
}
