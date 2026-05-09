package com.example.umc_10th.data.repository

import com.example.umc_10th.api.UserData

interface UserRemoteRepository {
    suspend fun getUser(userId: Int): UserData?
    suspend fun getUsers(page: Int): List<UserData>
}
