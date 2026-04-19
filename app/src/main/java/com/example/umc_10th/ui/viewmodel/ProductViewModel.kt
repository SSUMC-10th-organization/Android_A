package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.model.Product
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository // Hilt가 자동으로 주입
) : ViewModel() {

    // 1. 홈 화면 상품 목록 상태
    private val _homeProducts = MutableStateFlow<List<Product>>(emptyList())
    val homeProducts: StateFlow<List<Product>> = _homeProducts

    // 2. 구매 내역 목록 상태
    private val _purchaseHistory = MutableStateFlow<List<PurchaseProduct>>(emptyList())
    val purchaseHistory: StateFlow<List<PurchaseProduct>> = _purchaseHistory

    // 홈 상품 목록 불러오기
    fun fetchHomeProducts() {
        viewModelScope.launch {
            try {
                val products = productRepository.getHomeProducts()
                _homeProducts.value = products
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    // 구매 내역 저장하기
    fun addPurchase(product: PurchaseProduct) {
        viewModelScope.launch {
            productRepository.insertPurchase(product)
            fetchPurchaseHistory() // 저장 후 목록 새로고침
        }
    }

    // 구매 내역 불러오기
    fun fetchPurchaseHistory() {
        viewModelScope.launch {
            val history = productRepository.getAllPurchases()
            _purchaseHistory.value = history
        }
    }
}