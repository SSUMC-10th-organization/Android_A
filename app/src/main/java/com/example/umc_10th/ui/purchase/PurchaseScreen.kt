package com.example.umc_10th.ui.purchase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.ui.product.AllViewModel

@Composable
fun PurchaseScreen(
    viewModel: AllViewModel = hiltViewModel()
) {
    val purchaseList by viewModel.purchaseList.collectAsStateWithLifecycle()

    // 화면 진입 시 데이터 로드
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 상단 타이틀 (나이키 앱 특유의 깔끔한 헤더)
        Text(
            text = "Shoes",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        // 2열 그리드 리스트 (item_purchase.xml 디자인 반영)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(purchaseList) { product ->
                PurchaseProductItem(
                    product = product,
                    onFavoriteClick = { viewModel.toggleFavorite(product) }
                )
            }
        }
    }
}

@Composable
fun PurchaseProductItem(
    product: PurchaseProduct,
    onFavoriteClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 이미지 섹션 (1:1 비율 고정)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // 하트 버튼 (Storage 로직 연결)
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(6.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (product.isFavorite) R.drawable.ic_favorite_heart
                        else R.drawable.ic_favorite_border
                    ),
                    contentDescription = null,
                    tint = if (product.isFavorite) Color.Red else Color.Black
                )
            }
        }

        // 텍스트 섹션 (최대 3줄 철학 유지)
        Text(
            text = product.name,
            modifier = Modifier.padding(top = 10.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )

        Text(
            text = product.explain,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 2,
            style = TextStyle(fontSize = 13.sp, color = Color(0xFFAAAAAA))
        )

        Text(
            text = product.price,
            modifier = Modifier.padding(top = 6.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )
    }
}