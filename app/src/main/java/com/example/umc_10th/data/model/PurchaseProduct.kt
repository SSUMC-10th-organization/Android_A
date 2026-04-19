package com.example.umc_10th.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "purchase_table")
data class PurchaseProduct(
    @PrimaryKey(autoGenerate = true) val purchaseId: Int = 0,
    val imageRes: Int,    // 👈 'imageRes' 하나만 남기고 타입을 Int로 통일!
    val name: String,     // 상품명
    val explain: String,  // 설명
    val price: String,    // 가격
    var isFavorite: Boolean = false // 찜 여부
)