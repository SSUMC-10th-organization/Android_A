package com.example.umc_10th.data

// ⭐ object는 싱글톤(Singleton)으로, 앱 어디서든 하나의 리스트를 공유하게 해줍니다.
object WishlistStorage {
    private val wishlist = mutableListOf<PurchaseProduct>()

    fun addProduct(product: PurchaseProduct) {
        if (!wishlist.any { it.name == product.name }) {
            wishlist.add(product)
        }
    }

    fun removeProduct(product: PurchaseProduct) {
        // 이름이 같은 상품을 찾아서 제거
        wishlist.removeAll { it.name == product.name }
    }

    fun getWishlist(): MutableList<PurchaseProduct> = wishlist

    // ⭐ [추가] 특정 상품이 위시리스트에 있는지 확인하는 함수
    fun isFavorite(productName: String): Boolean {
        return wishlist.any { it.name == productName }
    }
}