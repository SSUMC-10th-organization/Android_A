package com.example.umc_10th.data

// 1. 단일 유저 응답을 위한 클래스 (이걸 추가해!)
data class UserResponse(
    val data: UserData  // List<UserData>가 아니라 그냥 UserData임에 주의!
)

// 2. 리스트 응답을 위한 클래스 (이미 있을 거야)
data class UserListResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserData>
)

// 3. 공통 유저 데이터 (이미 있을 거야)
data class UserData(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)