package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.ProductLocalRepository
import com.example.umc_10th.ui.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val localRepository: ProductLocalRepository
) : ViewModel() {

    val likedProducts: StateFlow<List<ProductData>> = localRepository.getPurchaseProducts()
        .map { products -> products.filter { it.isLiked } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
