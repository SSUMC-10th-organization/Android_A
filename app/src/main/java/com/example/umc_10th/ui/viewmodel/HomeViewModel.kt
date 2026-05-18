package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.ProductLocalRepository
import com.example.umc_10th.ui.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepository: ProductLocalRepository
) : ViewModel() {

    val homeProducts: StateFlow<List<ProductData>> = localRepository.getHomeProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun initializeIfEmpty(dummyHome: List<ProductData>, dummyPurchase: List<ProductData>) {
        viewModelScope.launch {
            val existing = localRepository.getHomeProducts().first()
            if (existing.isEmpty()) {
                localRepository.saveHomeProducts(dummyHome)
                localRepository.savePurchaseProducts(dummyPurchase)
            }
        }
    }
}
