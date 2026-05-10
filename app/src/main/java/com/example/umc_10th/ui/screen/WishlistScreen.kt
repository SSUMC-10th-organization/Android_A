package com.example.umc_10th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.umc_10th.Product
import com.example.umc_10th.R
import com.example.umc_10th.WishlistViewModel
import com.example.umc_10th.ui.theme.Gray700
import com.example.umc_10th.ui.theme.NotoSans
import com.example.umc_10th.ui.theme.Orange500

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val products by viewModel.wishlistProducts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 28.dp)
    ) {
        Text(
            text = "위시리스트",
            fontSize = 28.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(products, key = { it.id }) { product ->
                WishlistProductItem(
                    product = product,
                    onFavoriteClick = { viewModel.toggleFavorite(product.id, !product.isFavorite) },
                    onItemClick = {}
                )
            }
        }
    }
}

@Composable
private fun WishlistProductItem(
    product: Product,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onItemClick)
    ) {
        Box {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = 12.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onFavoriteClick),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        if (product.isFavorite) R.drawable.ic_heart_filled
                        else R.drawable.ic_heart_empty
                    ),
                    contentDescription = "좋아요",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp)) {
            if (product.isBestSeller) {
                Text(
                    text = "BestSeller",
                    fontSize = 14.sp,
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Medium,
                    color = Orange500,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Text(
                text = product.description,
                fontSize = 14.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Normal,
                color = Gray700,
                modifier = Modifier.padding(top = 2.dp)
            )

            if (product.colorInfo.isNotEmpty()) {
                Text(
                    text = product.colorInfo,
                    fontSize = 14.sp,
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Normal,
                    color = Gray700,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Text(
                text = product.price,
                fontSize = 14.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
