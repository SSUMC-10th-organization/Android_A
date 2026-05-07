package com.example.umc_10th.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.umc_10th.R
import com.example.umc_10th.ui.viewmodel.UserViewModel
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val userList by viewModel.userList.collectAsStateWithLifecycle()
    val myInfo by viewModel.myInfo.collectAsStateWithLifecycle()

    // 데이터 호출
    LaunchedEffect(Unit) {
        viewModel.fetchProfileData(page = 1, myId = 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()) // NestedScrollView 역할
    ) {
        // 1. 상단 프로필 섹션
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = myInfo?.avatar,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = myInfo?.let { "${it.first_name} ${it.last_name}" } ?: "닉네임",
                modifier = Modifier.padding(top = 16.dp),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = { /* 프로필 수정 */ },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(150.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("프로필 수정", color = Color.Black, fontSize = 14.sp)
            }
        }

        // 2. 4단 메뉴 섹션 (주문, 패스, 이벤트, 설정)
        ProfileMenuRow()

        Divider(modifier = Modifier.height(8.dp), color = Color(0xFFF5F5F5))

        // 3. 멤버 혜택 섹션
        ProfileLinkItem(title = "나이키 멤버 혜택", subtitle = "0개 사용 가능")

        Divider(modifier = Modifier.height(8.dp), color = Color(0xFFF5F5F5))

        // 4. 팔로잉 섹션 (서버 데이터 리스트)
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("팔로잉 (${userList.size})", fontWeight = FontWeight.Bold)
                Text("편집", color = Color.Gray)
            }

            LazyRow(
                contentPadding = PaddingValues(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(userList) { user ->
                    AsyncImage(
                        model = user.avatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE0E0E0)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // 5. 푸터
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF9F9F9))
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("회원 가입일: 2025년 9월", color = Color.LightGray, fontSize = 12.sp)
        }
    }
}



@Composable
fun ProfileMenuRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MenuItem(R.drawable.ic_order, "주문")
        VerticalDivider()
        MenuItem(R.drawable.ic_pass, "패스")
        VerticalDivider()
        MenuItem(R.drawable.ic_event, "이벤트")
        VerticalDivider()
        MenuItem(R.drawable.ic_settings, "설정")
    }
}

@Composable
fun MenuItem(iconRes: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(24.dp))
        Text(text, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ProfileLinkItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Icon(painterResource(id = R.drawable.ic_arrow_right), contentDescription = null, modifier = Modifier.size(20.dp))
    }
}

@Composable
fun VerticalDivider() {
    Box(modifier = Modifier.width(1.dp).height(30.dp).background(Color.LightGray))
}