package com.example.umc_10th.ui.shoppingcart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.umc_10th.ui.navigation.Screen

@Composable
fun ShoppingCartScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("장바구니가 비어 있습니다.")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // [미션 포인트] 클릭 시 구매하기 탭(Route)으로 이동
            navController.navigate(Screen.Purchase.route)
        }) {
            Text("주문하기")
        }
    }
}