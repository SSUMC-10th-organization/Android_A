package com.example.umc_10th.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun ProductCard(
    product: ProductUiModel,
    onLikeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(170.dp)
            .padding(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(160.dp)
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { onLikeClick(product.id) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(36.dp)
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (product.isLiked) {
                                R.drawable.ic_heart_filled
                            } else {
                                R.drawable.ic_heart_empty
                            }
                        ),
                        contentDescription = if (product.isLiked) "찜 해제" else "찜하기",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = "BestSeller",
                modifier = Modifier.padding(top = 8.dp),
                color = Color(0xFFD67A1F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = product.name,
                modifier = Modifier.padding(top = 2.dp),
                color = Color(0xFF111111),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.category,
                modifier = Modifier.padding(top = 2.dp),
                color = Color(0xFF888888),
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.colorCount,
                modifier = Modifier.padding(top = 2.dp),
                color = Color(0xFF888888),
                fontSize = 12.sp
            )

            Text(
                text = product.price,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                color = Color(0xFF111111),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HomeProductCard(
    product: ProductUiModel
) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .padding(end = 16.dp)
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .width(220.dp)
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = product.name,
            modifier = Modifier.padding(top = 12.dp),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = product.price,
            modifier = Modifier.padding(top = 6.dp),
            color = Color(0xFF777777),
            fontSize = 16.sp
        )
    }
}