package com.example.umc_10th.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResApi {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): SingleUserResponse

    @GET("users")
    suspend fun getUserList(@Query("page") page: Int = 1): UserListResponse
}
