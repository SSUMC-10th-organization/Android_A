package com.example.umc_10th.domain.repository

import com.example.umc_10th.data.model.Product
import com.example.umc_10th.data.model.PurchaseProduct

interface ProductRepository {
    // 상품 리스트 가져오기 (Home/All 화면용)
    suspend fun getHomeProducts(): List<Product>

    // 구매 내역 저장 및 가져오기 (Wishlist 화면용)
    suspend fun insertPurchase(product: PurchaseProduct)
    suspend fun getAllPurchases(): List<PurchaseProduct>
}