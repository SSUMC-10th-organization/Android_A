package com.example.umc_10th.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.umc_10th.ui.shoppingcart.ShoppingCartScreen

@Composable
fun NikeNavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(innerPadding) // Scaffold의 패딩을 적용하여 바텀바와 겹치지 않게 함
    ) {
        composable(Screen.Home.route) {
            // 나중에 HomeScreen()으로 교체
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("홈") }
        }
        composable(Screen.Purchase.route) {
            // 나중에 PurchaseScreen()으로 교체
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("구매하기") }
        }
        composable(Screen.Wishlist.route) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("위시리스트") }
        }
        composable(Screen.Cart.route) {
            // 7주차 미션의 핵심인 '장바구니' 화면 (주문하기 버튼 배치 예정)
            ShoppingCartScreen(navController)
        }
        composable(Screen.Profile.route) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("프로필") }
        }
    }
}