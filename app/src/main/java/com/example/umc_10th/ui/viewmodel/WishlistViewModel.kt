package com.example.umc_10th.ui.product

import androidx.lifecycle.ViewModel
import com.example.umc_10th.data.local.WishlistStorage
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.ui.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistStorage: WishlistStorage

) : ViewModel() {

    private val _wishlist = MutableStateFlow<List<PurchaseProduct>>(emptyList())
    val wishlist: StateFlow<List<PurchaseProduct>> = _wishlist.asStateFlow()

    // 1. 위시리스트 불러오기
    fun fetchWishlist() {
        _wishlist.value = MainActivity.Companion.wishlistStorage.getWishlist().toList()
    }

    // 2. 위시리스트에서 삭제하기
    fun removeFromWishlist(product: PurchaseProduct) {
        // 1. 저장소에서 데이터 삭제
        wishlistStorage.removeProduct(product)

        // 2. [핵심] 삭제된 후의 최신 리스트를 다시 가져와서 StateFlow 업데이트!
        // 이렇게 하면 AllFragment 때처럼 관찰(Collect) 중인 Fragment가 소식을 듣고 UI를 바꿉니다.
        fetchWishlist()
    }
}