package com.example.umc_10th.data.local

import com.example.umc_10th.data.model.PurchaseProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // 앱 전체에서 위시리스트 저장소는 딱 하나만 존재해야 하므로 추가
class WishlistStorage @Inject constructor( // @Inject constructor 추가 (Hilt가 생성법을 알게 됨)
    private val preferenceManager: SharedPreferenceManager
) {

    // 1. 초기화 및 메모리 캐시
    private val gson = Gson()
    private val wishlist = mutableListOf<PurchaseProduct>()

    // ... 아래 기존 함수들(loadFromDataStore, addProduct 등)은 그대로 두시면 됩니다! ...

    fun loadFromDataStore() {
        CoroutineScope(Dispatchers.IO).launch {
            val type = object : TypeToken<List<PurchaseProduct>>() {}.type
            preferenceManager.getObjectList<PurchaseProduct>(
                SharedPreferenceManager.KEY_WISHLIST,
                type
            ).first().let { savedList ->
                wishlist.clear()
                wishlist.addAll(savedList)
            }
        }
    }

    fun addProduct(product: PurchaseProduct) {
        if (!wishlist.any { it.name == product.name }) {
            wishlist.add(product)
            saveToDataStore()
        }
    }

    fun removeProduct(product: PurchaseProduct) {
        wishlist.removeAll { it.name == product.name }
        saveToDataStore()
    }

    fun getWishlist(): List<PurchaseProduct> = wishlist

    fun isFavorite(productName: String): Boolean {
        return wishlist.any { it.name == productName }
    }

    private fun saveToDataStore() {
        CoroutineScope(Dispatchers.IO).launch {
            preferenceManager.saveObjectList(
                SharedPreferenceManager.KEY_WISHLIST,
                wishlist
            )
        }
    }
}