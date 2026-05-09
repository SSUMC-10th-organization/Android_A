package com.example.umc_10th

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.ProductLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductLocalRepository
) : ViewModel() {

    fun toggleFavorite(productId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateProductFavorite(productId, isFavorite)
        }
    }
}
