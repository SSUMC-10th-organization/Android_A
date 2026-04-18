package com.example.umc_10th.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. 단일 유저 응답을 위한 클래스
data class UserResponse(
    val data: UserData
)

// 2. 리스트 응답을 위한 클래스
data class UserListResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<UserData>
)

// 3. 공통 유저 데이터
@Entity(tableName = "user_table")
data class UserData(
    @PrimaryKey val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)