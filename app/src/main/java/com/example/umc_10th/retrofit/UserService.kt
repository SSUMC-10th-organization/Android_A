package com.example.umc_10th.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("api/users/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
        @Header("x-api-key") apiKey: String
    ): Response<UserResponse>

    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Header("x-api-key") apiKey: String
    ): Response<UserListResponse>
}