package com.example.umc_10th.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_10th.R

@Composable
fun HomeScreen(
    products: List<ProductUiModel>,
    onLikeClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Discover",
            modifier = Modifier.padding(start = 30.dp, top = 60.dp),
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "9월 4일 목요일",
            modifier = Modifier.padding(start = 35.dp, top = 8.dp),
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.img_home_banner),
            contentDescription = "홈 배너",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 24.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "What's new",
            modifier = Modifier.padding(start = 28.dp, top = 28.dp),
            color = Color(0xFF444444),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "나이키 최신 상품",
            modifier = Modifier.padding(start = 28.dp, top = 6.dp),
            color = Color(0xFF111111),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            modifier = Modifier.padding(top = 18.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(products, key = { it.id }) { product ->
                HomeProductCard(product = product)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}