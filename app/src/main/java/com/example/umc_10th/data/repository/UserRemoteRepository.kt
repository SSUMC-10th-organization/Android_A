package com.example.umc_10th.data.repository

import com.example.umc_10th.network.ReqResApi
import com.example.umc_10th.network.UserData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteRepository @Inject constructor(
    private val api: ReqResApi
) {
    suspend fun getUser(id: Int): UserData = api.getUser(id).data

    suspend fun getUserList(page: Int = 1): List<UserData> = api.getUserList(page).data
}
