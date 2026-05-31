package com.example.umc_10th.ui.profile

import com.example.umc_10th.retrofit.UserData

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: UserData? = null,
    val followingUsers: List<UserData> = emptyList(),
    val errorMessage: String? = null
)