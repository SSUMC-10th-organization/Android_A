package com.example.umc_10th

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.ProductLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopAllViewModel @Inject constructor(
    private val repository: ProductLocalRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeProductsIfEmpty()

            repository.getProductsFlow().collect { products ->
                _products.value = products
            }
        }
    }

    fun toggleFavorite(productId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateProductFavorite(productId, isFavorite)
        }
    }
}
