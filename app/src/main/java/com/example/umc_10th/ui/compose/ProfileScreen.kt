package com.example.umc_10th.ui.compose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_10th.R
import com.example.umc_10th.retrofit.ApiClient
import com.example.umc_10th.retrofit.UserData
import com.example.umc_10th.retrofit.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun ProfileScreen() {
    var user by remember { mutableStateOf<UserData?>(null) }
    var followingUsers by remember { mutableStateOf<List<UserData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null

        val repository = UserRepository(ApiClient.userService)

        val userResult = repository.getUser(id = 1)

        userResult
            .onSuccess { userData ->
                user = userData
            }
            .onFailure { error ->
                errorMessage = error.message ?: "프로필 정보를 불러오지 못했습니다."
            }

        val listResult = repository.getUserList(page = 1)

        listResult
            .onSuccess { users ->
                followingUsers = users
                    .filter { it.id != 1 }
                    .take(3)
            }
            .onFailure {
                followingUsers = emptyList()
            }

        isLoading = false
    }

    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "프로필 로딩 중...")
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red
                    )
                }
            }

            else -> {
                ProfileTopSection(user = user)
            }
        }

        ProfileMenuSection()
        GrayDivider()
        MemberBenefitSection()
        GrayDivider()
        FollowingSection(followingUsers = followingUsers)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFF2F2F2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "회원 가입일 : 2025년 9월",
                color = Color(0xFF888888),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun ProfileTopSection(
    user: UserData?
) {
    val nickname = if (user != null) {
        "${user.firstName} ${user.lastName}"
    } else {
        "닉네임"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkProfileImage(
            imageUrl = user?.avatar,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Text(
            text = nickname,
            modifier = Modifier.padding(top = 20.dp),
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(text = "프로필 수정")
        }
    }
}

@Composable
private fun NetworkProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(imageUrl) {
        if (!imageUrl.isNullOrBlank()) {
            bitmap = withContext(Dispatchers.IO) {
                try {
                    val inputStream = URL(imageUrl).openStream()
                    BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "프로필 이미지",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_sampleface),
            contentDescription = "기본 프로필 이미지",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ProfileMenuSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ProfileMenuItem(
            iconRes = R.drawable.ic_profile_order,
            title = "주문",
            onClick = { }
        )

        ProfileMenuItem(
            iconRes = R.drawable.ic_profile_pass,
            title = "패스",
            onClick = { }
        )

        ProfileMenuItem(
            iconRes = R.drawable.ic_profile_event,
            title = "이벤트",
            onClick = { }
        )

        ProfileMenuItem(
            iconRes = R.drawable.ic_profile_setting,
            title = "설정",
            onClick = { }
        )
    }
}

@Composable
private fun ProfileMenuItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun MemberBenefitSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "나이키 멤버 혜택",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "0개 사용 가능",
                modifier = Modifier.padding(top = 4.dp),
                color = Color(0xFF666666),
                fontSize = 16.sp
            )
        }

        IconButton(
            onClick = { }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_point),
                contentDescription = "나이키 멤버 혜택 이동",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun FollowingSection(
    followingUsers: List<UserData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "팔로잉 (${followingUsers.size})",
                modifier = Modifier.weight(1f),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = { }) {
                Text(
                    text = "편집",
                    color = Color(0xFF777777)
                )
            }
        }

        if (followingUsers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "팔로잉 목록이 없습니다.",
                    color = Color(0xFF888888),
                    fontSize = 14.sp
                )
            }
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
            ) {
                items(
                    items = followingUsers,
                    key = { it.id }
                ) { followingUser ->
                    FollowingUserItem(user = followingUser)
                }
            }
        }
    }
}

@Composable
private fun FollowingUserItem(
    user: UserData
) {
    Column(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkProfileImage(
            imageUrl = user.avatar,
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
private fun GrayDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(Color(0xFFF2F2F2))
    )
}