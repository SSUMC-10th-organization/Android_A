package com.example.umc_10th.ui.wishlist

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
import com.example.umc_10th.ui.product.WishlistViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val wishlist by viewModel.wishlist.collectAsStateWithLifecycle()

    // 화면 진입 시 데이터 로드
    LaunchedEffect(Unit) {
        viewModel.fetchWishlist()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 타이틀 영역
        Text(
            text = "위시리스트",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        if (wishlist.isEmpty()) {
            // 위시리스트가 비었을 때 (선택 사항)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("위시리스트가 비어 있습니다.", color = Color.Gray)
            }
        } else {
            // 2열 그리드 리스트
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(wishlist) { product ->
                    WishlistProductItem(
                        product = product,
                        onHeartClick = { viewModel.removeFromWishlist(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun WishlistProductItem(
    product: PurchaseProduct,
    onHeartClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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

            // 하트 버튼 (위시리스트이므로 항상 빨간색/채워진 하트)
            IconButton(
                onClick = onHeartClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(6.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite_heart), // 빨간 하트 리소스
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

        // 텍스트 섹션 (최대 3줄 이내 미니멀 디자인)
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
            maxLines = 1, // 위시리스트는 더 깔끔하게 1줄 처리 권장
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 13.sp, color = Color(0xFFAAAAAA))
        )

        Text(
            text = product.price,
            modifier = Modifier.padding(top = 6.dp),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )
    }
}