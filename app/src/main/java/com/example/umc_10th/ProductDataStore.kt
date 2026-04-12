package com.example.umc_10th

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.productDataStore: DataStore<Preferences> by preferencesDataStore(name = "products")

private val PRODUCTS_JSON_KEY = stringPreferencesKey("products_json")
private val gson = Gson()

suspend fun saveProducts(context: Context, products: List<Product>) {
    val jsonString = gson.toJson(products)
    context.productDataStore.edit { prefs ->
        prefs[PRODUCTS_JSON_KEY] = jsonString
    }
}

fun getProductsFlow(context: Context): Flow<List<Product>> {
    return context.productDataStore.data.map { prefs ->
        val jsonString = prefs[PRODUCTS_JSON_KEY] ?: return@map emptyList()
        val type = object : TypeToken<List<Product>>() {}.type
        val products: List<Product> = gson.fromJson(jsonString, type) ?: emptyList()
        products.map { product ->
            val resId = context.resources.getIdentifier(
                "img_product_${product.id}", "drawable", context.packageName
            )
            if (resId != 0) product.copy(imageRes = resId) else product
        }
    }
}

suspend fun initializeProductsIfEmpty(context: Context) {
    val current = context.productDataStore.data.first()
    if (current[PRODUCTS_JSON_KEY] == null) {
        saveProducts(context, DummyData.getProducts())
    }
}

suspend fun updateProductFavorite(context: Context, productId: Int, isFavorite: Boolean) {
    val products = getProductsFlow(context).first().toMutableList()
    val index = products.indexOfFirst { it.id == productId }
    if (index != -1) {
        products[index] = products[index].copy(isFavorite = isFavorite)
        saveProducts(context, products)
    }
}
