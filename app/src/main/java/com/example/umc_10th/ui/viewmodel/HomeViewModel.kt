package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.R
import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.model.Product
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefManager: SharedPreferenceManager
) : ViewModel() {

    // 화면에 보여줄 상품 리스트 상태 (StateFlow)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun fetchHomeProducts() {
        val type = object : TypeToken<List<Product>>() {}.type

        viewModelScope.launch {
            // 1. 데이터 로드
            val savedList = prefManager.getObjectList<Product>(
                SharedPreferenceManager.KEY_HOME_PRODUCTS, type
            ).first()

            if (savedList.isEmpty()) {
                // 2. 없으면 더미 생성 및 저장
                val dummy = listOf(
                    Product(imageRes = R.drawable.shoes1, name = "Air Jordan XXXVI", price = "US$185"),
                    Product(imageRes = R.drawable.shoes2, name = "Nike Air Force 1 '07", price = "US$115")
                )
                prefManager.saveObjectList(SharedPreferenceManager.KEY_HOME_PRODUCTS, dummy)
                _products.value = dummy
            } else {
                // 3. 있으면 상태 업데이트
                _products.value = savedList
            }
        }
    }
}