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
class WishlistViewModel @Inject constructor(
    private val repository: ProductLocalRepository
) : ViewModel() {

    private val _wishlistProducts = MutableStateFlow<List<Product>>(emptyList())
    val wishlistProducts: StateFlow<List<Product>> = _wishlistProducts.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeProductsIfEmpty()

            repository.getProductsFlow().collect { allProducts ->
                _wishlistProducts.value = allProducts.filter { it.isFavorite }
            }
        }
    }

    fun toggleFavorite(productId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateProductFavorite(productId, isFavorite)
        }
    }
}
