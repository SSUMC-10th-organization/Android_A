package com.example.umc_10th.network

data class UserData(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

data class SingleUserResponse(
    val data: UserData
)

data class UserListResponse(
    val data: List<UserData>,
    val page: Int,
    val total: Int
)
