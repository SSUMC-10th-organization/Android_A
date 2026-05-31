package com.example.umc_10th.ui.purchase

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_10th.R
import com.example.umc_10th.data.model.ProductData

@Composable
fun PurchaseProductGrid(
    products: List<ProductData>,
    onLikeClick: (ProductData) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            top = 12.dp,
            bottom = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = products,
            key = { product -> product.id }
        ) { product ->
            PurchaseProductItem(
                product = product,
                onLikeClick = onLikeClick
            )
        }
    }
}

@Composable
fun PurchaseProductItem(
    product: ProductData,
    onLikeClick: (ProductData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { onLikeClick(product) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(36.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = if (product.isLiked) {
                            R.drawable.ic_heart_filled
                        } else {
                            R.drawable.ic_heart_empty
                        }
                    ),
                    contentDescription = "찜 버튼",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (product.isBestSeller) {
            Text(
                text = "BestSeller",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD67A1F)
            )

            Spacer(modifier = Modifier.height(2.dp))
        }

        Text(
            text = product.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111111),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = product.category,
            fontSize = 12.sp,
            color = Color(0xFF888888),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${product.colorCount} Colours",
            fontSize = 12.sp,
            color = Color(0xFF888888)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = product.price,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111111)
        )
    }
}