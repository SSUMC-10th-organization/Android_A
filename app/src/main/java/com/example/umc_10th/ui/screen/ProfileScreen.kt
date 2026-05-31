package com.example.umc_10th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.umc_10th.ProfileViewModel
import com.example.umc_10th.R
import com.example.umc_10th.ui.theme.Gray200
import com.example.umc_10th.ui.theme.Gray400
import com.example.umc_10th.ui.theme.Gray50
import com.example.umc_10th.ui.theme.Gray700
import com.example.umc_10th.ui.theme.NotoSans

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val followingList by viewModel.followingList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.prefetch()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 21.dp, start = 24.dp, end = 24.dp)
        ) {
            if (userProfile?.avatar != null) {
                AsyncImage(
                    model = userProfile!!.avatar,
                    contentDescription = "프로필 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.profile_ellipse),
                    contentDescription = "프로필 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(84.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = userProfile?.let { "${it.firstName} ${it.lastName}" } ?: "닉네임",
                fontSize = 20.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                border = BorderStroke(1.dp, Gray200),
                modifier = Modifier
                    .width(180.dp)
                    .height(51.dp)
            ) {
                Text(
                    text = "프로필 수정",
                    fontSize = 16.sp,
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 주문/패스/이벤트/설정 메뉴
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 42.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileMenuItem(iconRes = R.drawable.ic_archive, label = "주문")
            VerticalDivider()
            ProfileMenuItem(iconRes = R.drawable.ic_identification_card, label = "패스")
            VerticalDivider()
            ProfileMenuItem(iconRes = R.drawable.ic_calendar_blank, label = "이벤트")
            VerticalDivider()
            ProfileMenuItem(iconRes = R.drawable.ic_gear, label = "설정")
        }

        // 구분선
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(top = 25.dp)
                .background(Gray50)
        )

        Spacer(modifier = Modifier.height(25.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Gray50)
        )

        // 나이키 멤버 혜택
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "나이키 멤버 혜택",
                    fontSize = 16.sp,
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "0개 사용 가능",
                    fontSize = 12.sp,
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Normal,
                    color = Gray700,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = "더보기",
                modifier = Modifier.size(width = 8.dp, height = 14.dp)
            )
        }

        // 구분선
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Gray50)
        )

        // 팔로잉 섹션
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 28.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "팔로잉 (${followingList.size})",
                fontSize = 14.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "편집",
                fontSize = 12.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Normal,
                color = Gray700
            )
        }

        val pagerState = rememberPagerState(pageCount = { followingList.size })

        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(106.dp),
            pageSpacing = 6.dp,
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp)
        ) { page ->
            val user = followingList[page]
            AsyncImage(
                model = user.avatar,
                contentDescription = "${user.firstName} ${user.lastName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(106.dp)
            )
        }

        Spacer(modifier = Modifier.height(97.dp))

        // 회원 가입일
        Text(
            text = "회원 가입일: 2025년 9월",
            fontSize = 12.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            color = Gray700,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray50)
                .padding(vertical = 18.dp)
        )
    }
}

@Composable
private fun ProfileMenuItem(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(31.dp)
            .background(Gray400)
    )
}
