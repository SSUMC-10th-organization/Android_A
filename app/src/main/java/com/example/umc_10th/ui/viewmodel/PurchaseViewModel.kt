package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.ProductLocalRepository
import com.example.umc_10th.ui.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val localRepository: ProductLocalRepository
) : ViewModel() {

    val purchaseProducts: StateFlow<List<ProductData>> = localRepository.getPurchaseProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateLiked(index: Int, isLiked: Boolean) {
        viewModelScope.launch {
            localRepository.updateProductLiked(index, isLiked)
        }
    }
}
