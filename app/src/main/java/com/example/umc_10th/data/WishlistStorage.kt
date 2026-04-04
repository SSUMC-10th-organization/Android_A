package com.example.umc_10th.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class WishlistStorage(private val preferenceManager: SharedPreferenceManager) {

    // 메모리 캐시 (화면에 바로 보여주기 위함)
    private val gson = Gson()
    private val wishlist = mutableListOf<PurchaseProduct>()

    // ⭐ 앱 시작 시 호출할 초기화 함수
    fun loadFromDataStore() {
        CoroutineScope(Dispatchers.IO).launch {
            // Flow에서 데이터를 한 번만 가져옵니다 (.first())
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

    // 위시리스트 추가 (DataStore에도 저장)
    fun addProduct(product: PurchaseProduct) {
        if (!wishlist.any { it.name == product.name }) {
            wishlist.add(product)
            saveToDataStore() // 변경사항 저장
        }
    }

    // 위시리스트 삭제 (DataStore에도 반영)
    fun removeProduct(product: PurchaseProduct) {
        wishlist.removeAll { it.name == product.name }
        saveToDataStore() // 변경사항 저장
    }

    fun getWishlist(): List<PurchaseProduct> = wishlist

    fun isFavorite(productName: String): Boolean {
        return wishlist.any { it.name == productName }
    }

    // ⭐ 핵심: 현재 메모리 리스트를 JSON으로 구워서 DataStore에 저장
    private fun saveToDataStore() {
        CoroutineScope(Dispatchers.IO).launch {
            preferenceManager.saveObjectList(
                SharedPreferenceManager.KEY_WISHLIST,
                wishlist
            )
        }
    }
}