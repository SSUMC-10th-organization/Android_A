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
class HomeViewModel @Inject constructor(
    private val repository: ProductLocalRepository
) : ViewModel() {

    private val _homeProducts = MutableStateFlow<List<Product>>(emptyList())
    val homeProducts: StateFlow<List<Product>> = _homeProducts.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeProductsIfEmpty()

            repository.getProductsFlow().collect { allProducts ->
                val filtered = listOf(
                    allProducts.firstOrNull { it.id == 5 },
                    allProducts.firstOrNull { it.id == 3 }
                ).filterNotNull()
                _homeProducts.value = filtered
            }
        }
    }
}
