package com.example.umc_10th.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.umc_10th.R
import com.example.umc_10th.data.model.Product
import com.example.umc_10th.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    // 1. ViewModel 데이터 관찰
    val products by viewModel.products.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchHomeProducts()
    }

    // 2. 화면 전체 스크롤을 위한 LazyColumn (XML의 NestedScrollView 역할)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 헤더 섹션 (Discover & 날짜)
        item {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "Discover",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text(
                    text = "9월 4일 목요일", // 나중에 실시간 날짜 로직 연결 가능
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF767676)
                    )
                )
            }
        }

        // 메인 이미지 섹션 (image_section)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mainimage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        // 상품 헤더 섹션 (What's New)
        item {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "What's new",
                    style = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
                Text(
                    text = "나이키 최신 상품",
                    modifier = Modifier.padding(top = 6.dp),
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        }

        // 가로 스크롤 상품 리스트 (RecyclerView 역할)
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 20.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    HomeProductItem(product = product)
                }
            }
        }
        item {
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Composable
fun HomeProductItem(product: Product) {
    Column(
        modifier = Modifier.width(314.dp) // XML의 314dp 유지
    ) {
        // 상품 이미지
        Image(
            painter = painterResource(id = product.imageRes), // 헬퍼 함수 필요
            contentDescription = null,
            modifier = Modifier
                .size(314.dp)
                .clip(RectangleShape),
            contentScale = ContentScale.Crop
        )

        // 상품 이름 (최대 2줄 유지)
        Text(
            text = product.name,
            modifier = Modifier
                .width(160.dp)
                .padding(top = 12.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )
        )

        // 상품 가격
        Text(
            text = "${product.price}",
            modifier = Modifier.padding(top = 6.dp),
            style = TextStyle(
                fontSize = 13.sp,
                color = Color(0xFF767676)
            )
        )
    }
}