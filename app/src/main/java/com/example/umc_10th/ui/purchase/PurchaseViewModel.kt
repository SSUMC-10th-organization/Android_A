package com.example.umc_10th.ui.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.model.ProductData
import com.example.umc_10th.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PurchaseUiState(
    val products: List<ProductData> = emptyList()
)

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseUiState())
    val uiState: StateFlow<PurchaseUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.initializeProductsIfEmpty()

            productRepository.getProductsFlow().collect { productList ->
                _uiState.value = PurchaseUiState(
                    products = productList
                )
            }
        }
    }

    fun toggleLike(productId: Int) {
        viewModelScope.launch {
            productRepository.toggleLike(productId)
        }
    }
}