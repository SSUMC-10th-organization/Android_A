package com.example.umc_10th.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.umc_10th.ShopAllViewModel
import com.example.umc_10th.ui.theme.Gray700
import com.example.umc_10th.ui.theme.NotoSans
import com.example.umc_10th.ui.theme.Orange500
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopScreen() {
    val tabTitles = listOf("전체", "Tops & T-Shirts", "Sale")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = Color.Black
                )
            },
            modifier = Modifier.padding(start = 9.dp, end = 9.dp, top = 15.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Gray700
                ) {
                    Text(
                        text = title,
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ShopAllPage()
                1 -> EmptyPage()
                2 -> EmptyPage()
            }
        }
    }
}

@Composable
private fun ShopAllPage(
    viewModel: ShopAllViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(products) { product ->
            ProductGridItem(
                product = product,
                onFavoriteClick = { viewModel.toggleFavorite(product.id, !product.isFavorite) },
                onItemClick = {}
            )
        }
    }
}

@Composable
private fun EmptyPage() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    )
}

@Composable
private fun ProductGridItem(
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
