package com.example.umc_10th.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.R
import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.local.WishlistStorage
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.ui.main.MainActivity
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val prefManager: SharedPreferenceManager,
    private val wishlistStorage: WishlistStorage // 👈 직접 주입받음
) : ViewModel() {

    private val _purchaseList = MutableStateFlow<List<PurchaseProduct>>(emptyList())
    val purchaseList: StateFlow<List<PurchaseProduct>> = _purchaseList.asStateFlow()

    fun fetchProducts() {
        val type = object : TypeToken<List<PurchaseProduct>>() {}.type

        viewModelScope.launch {
            val savedList = prefManager.getObjectList<PurchaseProduct>(
                SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, type
            ).first()

            val currentList = if (savedList.isEmpty()) {
                val dummy = createDummyData()
                prefManager.saveObjectList(SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, dummy)
                dummy
            } else {
                savedList
            }

            // 하트 상태 최신화 후 방출
            _purchaseList.value = updateFavoriteStatus(currentList)
        }
    }

    // 👈 좋아요 클릭 시 호출할 함수 추가
// 👈 위시리스트 저장소의 실제 함수(addProduct, removeProduct)를 사용하여 구현
    fun toggleFavorite(product: PurchaseProduct) {
        if (wishlistStorage.isFavorite(product.name)) {
            wishlistStorage.removeProduct(product) // 이미 있다면 삭제
        } else {
            wishlistStorage.addProduct(product)    // 없다면 추가
        }
        refreshFavoriteStatus() // UI 갱신
    }

    // onResume 등에서 상태만 새로고침할 때 호출
    fun refreshFavoriteStatus() {
        _purchaseList.value = updateFavoriteStatus(_purchaseList.value)
    }

    private fun updateFavoriteStatus(list: List<PurchaseProduct>): List<PurchaseProduct> {
        return list.map { product ->
            product.copy().apply {
                isFavorite = MainActivity.Companion.wishlistStorage.isFavorite(name!!)
            }
        }
    }

    private fun createDummyData(): List<PurchaseProduct> {
        val dummy = mutableListOf(
            PurchaseProduct(
                imageRes = R.drawable.socks1,
                name = "Nike Everyday Plus\nCushioned",
                explain = "Training Ankle Socks (6 Pairs)\n5 Colours",
                price = "US$20"
            ),
            PurchaseProduct(
                imageRes = R.drawable.socks2,
                name = "Nike Elite Crew",
                explain = "Basketball Crew Socks\n3 Colours",
                price = "US$160"
            ),
            PurchaseProduct(
                imageRes = R.drawable.women_shoes,
                name = "Nike Air Force 1 '07",
                explain = "Classic Design",
                price = "US$115"
            ),
            PurchaseProduct(
                imageRes = R.drawable.men_shoes,
                name = "Jordan Essentials",
                explain = "Comfortable Fit",
                price = "US$35"
            ),
            PurchaseProduct(
                imageRes = R.drawable.socks1,
                name = "Nike Spark Lightweight",
                explain = "Breathable Fabric",
                price = "US$18"
            ),
            PurchaseProduct(
                imageRes = R.drawable.socks2,
                name = "Nike Multiplier",
                explain = "Performance Socks",
                price = "US$22"
            ),
            PurchaseProduct(
                imageRes = R.drawable.women_shoes,
                name = "Nike Air Max Pro",
                explain = "Air Cushioning",
                price = "US$180"
            ),
            PurchaseProduct(
                imageRes = R.drawable.men_shoes,
                name = "Nike Pegasus 40",
                explain = "Running Shoes",
                price = "US$130"
            )
        )
        return dummy

    }
}