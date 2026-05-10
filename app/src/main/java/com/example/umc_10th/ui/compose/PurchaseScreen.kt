package com.example.umc_10th.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun PurchaseScreen(
    products: List<ProductUiModel>,
    onLikeClick: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("All", "Sale", "Top T-shirts")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "구매하기",
            modifier = Modifier.padding(start = 15.dp, top = 16.dp),
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(top = 12.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Black else Color(0xFF888888)
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> ProductGrid(
                products = products,
                onLikeClick = onLikeClick
            )

            1 -> CenterTextScreen(text = "Sale 화면")
            2 -> CenterTextScreen(text = "Top T-shirts 화면")
        }
    }
}

@Composable
private fun ProductGrid(
    products: List<ProductUiModel>,
    onLikeClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 12.dp,
            bottom = 12.dp
        )
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                onLikeClick = onLikeClick
            )
        }
    }
}
@Composable
private fun CenterTextScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}