package com.example.umc_10th.data.local

import com.example.umc_10th.data.model.PurchaseProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class WishlistStorage(private val preferenceManager: SharedPreferenceManager) {

    // 1. 초기화 및 메모리 캐시
    private val gson = Gson()
    private val wishlist = mutableListOf<PurchaseProduct>()
    //mutableListOf :앱이 켜져 있는 동안 데이터를 빠르게 꺼내 쓰기 위해 메모리에 들고 있는 캐시 리스트

    fun loadFromDataStore() {
        //시작 시 저장소(Disk)에 있던 데이터를 메모리(wishlist)로 끌어올리는 함수
        CoroutineScope(Dispatchers.IO).launch {
            //파일 읽기는 시간이 걸리므로 UI 스레드가 아닌 입출력 전용 스레드에서 실행
            val type = object : TypeToken<List<PurchaseProduct>>() {}.type
            preferenceManager.getObjectList<PurchaseProduct>(
                SharedPreferenceManager.KEY_WISHLIST,
                type
            ).first().let { savedList ->
                //.first() : 데이터 흐름(Flow)을 계속 관찰하지 않고, 딱 한 번만 현재 값을 가져올 때 사용
                wishlist.clear()
                wishlist.addAll(savedList)
            }
        }
    }

    //2. 데이터 추가 및 중복 검사
    fun addProduct(product: PurchaseProduct) {
        //같은 이름의 상품이 이미 리스트에 있는지 검사하여 중복 추가를 방지
        if (!wishlist.any { it.name == product.name }) {
            wishlist.add(product)
            saveToDataStore() // 변경사항 저장, 어플 재실행 시에도 유지되게 함
        }
    }

    // 3. 데이터 삭제 및 상태 확인
    fun removeProduct(product: PurchaseProduct) {
        wishlist.removeAll { it.name == product.name }
        //조건에 맞는 아이템을 리스트에서 제거
        saveToDataStore()
        // 변경사항 저장
    }

    fun getWishlist(): List<PurchaseProduct> = wishlist

    fun isFavorite(productName: String): Boolean {
        //특정 상품이 현재 찜(하트 버튼 눌림) 상태인지 확인하는 함수
        return wishlist.any { it.name == productName }
    }

    // 4. 영구 저장 (Disk Write)
    private fun saveToDataStore() {
        //현재 메모리에 있는 wishlist 전체를 DataStore에 덮어씌음
        CoroutineScope(Dispatchers.IO).launch {
            //저장은 시간이 걸리는 작업이므로 launch를 통해 백그라운드에서 처리
            preferenceManager.saveObjectList(
                SharedPreferenceManager.KEY_WISHLIST,
                wishlist
            )
        }
    }
}