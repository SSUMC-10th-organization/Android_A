package com.example.umc_10th.data.repository

import com.example.umc_10th.api.UserData
import com.example.umc_10th.api.UserService
import javax.inject.Inject

class UserRemoteRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteRepository {

    override suspend fun getUser(userId: Int): UserData? {
        return try {
            val response = userService.getUser(userId)
            if (response.isSuccessful) {
                response.body()?.data
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getUsers(page: Int): List<UserData> {
        return try {
            val response = userService.getUsers(page)
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
