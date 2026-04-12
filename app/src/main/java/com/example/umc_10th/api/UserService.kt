package com.example.umc_10th.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): Response<UserResponse>
}