package com.example.umc_10th.data.remote

import com.example.umc_10th.data.UserListResponse
import com.example.umc_10th.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResInterface {

    // 1. 유저 리스트 가져오기 (팔로잉 목록용)
    @GET("api/users")
    fun getUserList(
        @Query("page") page: Int,
        @Header("x-api-key") apiKey: String = "reqres_772f01af212946ad89d8a90f83b08929"
    ): Call<UserListResponse>

    // 단일 유저 정보 가져오기 (userId를 먼저!)
    @GET("api/users/{id}") // {id}를 쓰면 더 유연하게 바꿀 수 있어
    fun getSingleUser(
        @Path("id") userId: Int, // 여기에 1이 들어갈 거야
        @Header("x-api-key") apiKey: String = "reqres_772f01af212946ad89d8a90f83b08929"
    ): Call<UserResponse>
}