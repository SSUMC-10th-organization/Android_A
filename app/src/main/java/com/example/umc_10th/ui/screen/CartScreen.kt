package com.example.umc_10th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_10th.R
import com.example.umc_10th.ui.theme.NotoSans

@Composable
fun CartScreen(onNavigateToShop: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_bag_circle),
                contentDescription = "장바구니 아이콘"
            )

            Spacer(modifier = Modifier.height(27.dp))

            Text(
                text = "장바구니가 비어 있습니다.\n제품을 추가하면 여기에 표시됩니다.",
                fontSize = 14.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onNavigateToShop,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RectangleShape,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 43.dp, end = 36.dp, bottom = 22.dp)
        ) {
            Text(
                text = "주문하기",
                fontSize = 16.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
