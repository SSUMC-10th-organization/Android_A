package com.example.umc_10th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.umc_10th.HomeViewModel
import com.example.umc_10th.Product
import com.example.umc_10th.R
import com.example.umc_10th.ui.theme.Beige
import com.example.umc_10th.ui.theme.Gray700
import com.example.umc_10th.ui.theme.NotoSans

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit = {}
) {
    val products by viewModel.homeProducts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 17.dp)
    ) {
        Text(
            text = "Discover",
            fontSize = 28.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 50.dp)
        )

        Text(
            text = "9월 4일 목요일",
            fontSize = 16.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            color = Gray700,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 6.dp, bottom = 50.dp)
        )

        Card(
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = Beige),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.home_logo),
                contentDescription = "배너 이미지",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp)
            )
        }

        Text(
            text = "What's new",
            fontSize = 16.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 40.dp)
        )

        Text(
            text = "나이키 최신 상품",
            fontSize = 28.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
            color = Gray700,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(start = 24.dp),
            modifier = Modifier.padding(top = 22.dp, bottom = 16.dp)
        ) {
            items(products) { product ->
                HomeProductItem(
                    product = product,
                    onClick = { onProductClick(product) }
                )
            }
        }
    }
}

@Composable
private fun HomeProductItem(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(314.dp)
            .padding(end = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(product.imageRes),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Text(
            text = product.name,
            fontSize = 14.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 1,
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = product.price,
            fontSize = 14.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            color = Gray700,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
