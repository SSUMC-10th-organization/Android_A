package com.example.umc_10th.ui.shoppingcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.umc_10th.R
import com.example.umc_10th.ui.navigation.Screen

@Composable
fun ShoppingCartScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1. 중앙 비어있음 알림 섹션 (이미지 + 텍스트)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 100.dp), // XML의 -200dp 마진 느낌 재현
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bagcircle_ic),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "장바구니가 비어있습니다.\n제품을 추가하면 여기에 표시됩니다.",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    lineHeight = 16.sp
                )
            )
        }

        // 2. 하단 주문하기 버튼 (MaterialButton 디자인 재현)
        Button(
            onClick = {
                // 클릭 시 구매하기(Purchase) 화면으로 이동
                navController.navigate(Screen.Purchase.route) {
                    // 스택이 쌓이지 않도록 정리 (선택 사항)
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 40.dp, vertical = 80.dp) // XML의 bias 0.8 위치 재현
                .height(60.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "주문하기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}