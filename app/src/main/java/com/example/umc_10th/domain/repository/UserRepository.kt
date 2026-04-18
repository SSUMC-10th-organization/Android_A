package com.example.umc_10th.domain.repository

import com.example.umc_10th.data.model.UserData
import com.example.umc_10th.data.model.UserListResponse

interface UserRepository {
    // 유저 리스트 가져오기 (Remote)
    suspend fun getUserList(page: Int): UserListResponse

    // 특정 유저 로컬 DB에 저장 (Local)
    suspend fun insertUser(user: UserData)

    // 로컬에 저장된 모든 유저 가져오기 (Local)
    suspend fun getAllUsers(): List<UserData>
}