package com.example.umc_10th.data.repository

import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.model.Product
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.domain.repository.ProductRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dataStoreManager: SharedPreferenceManager
) : ProductRepository {

    override suspend fun getHomeProducts(): List<Product> {
        val type = object : com.google.gson.reflect.TypeToken<List<Product>>() {}.type
        return dataStoreManager.getObjectList<Product>(SharedPreferenceManager.KEY_HOME_PRODUCTS, type).first()
    }

    override suspend fun insertPurchase(product: PurchaseProduct) {
        val type = object : com.google.gson.reflect.TypeToken<List<PurchaseProduct>>() {}.type
        val current = dataStoreManager.getObjectList<PurchaseProduct>(SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, type).first()
        dataStoreManager.saveObjectList(SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, current + product)
    }

    override suspend fun getAllPurchases(): List<PurchaseProduct> {
        val type = object : com.google.gson.reflect.TypeToken<List<PurchaseProduct>>() {}.type
        return dataStoreManager.getObjectList<PurchaseProduct>(SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, type).first()
    }
}