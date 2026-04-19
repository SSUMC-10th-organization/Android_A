package com.example.umc_10th.data.remote

import com.example.umc_10th.data.model.UserListResponse
import com.example.umc_10th.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

//실제 구현체는 Retrofit이 자동으로 만들어주기에 interface로 통신 규칙 정의
interface ReqResInterface {

    // 1. 유저 리스트 가져오기 (팔로잉 목록용)
    @GET("api/users")
    //서버 주소 뒤에 /api/users를 붙여서 데이터 가져옴(GET)
    suspend fun getUserList(
        @Query("page") page: Int,
        //URL 뒤에 ?page=2처럼 파라미터 붙임. (예: api/users?page=2) 페이징 처리에 사용
        @Header("x-api-key") apiKey: String = "reqres_772f01af212946ad89d8a90f83b08929"
        //HTTP 헤더에 인증키 담음. 보안이나 권한 확인을 위해 서버가 요구하는 정보
    ): UserListResponse
    //서버 응답을 UserListResponse라는 데이터 클래스 형태로 변환

    // 2. 단일 유저 정보 가져오기 (Path)
    @GET("api/users/{id}") // URL 경로 안에 변수({id})를 포함
    suspend fun getSingleUser(
        @Path("id") userId: Int, // 함수 인자로 들어온 userId를 위쪽의 {id} 자리에 삽입
        @Header("x-api-key") apiKey: String = "reqres_772f01af212946ad89d8a90f83b08929"
    ): UserResponse
    //단일 유저 정보이므로 리스트가 아닌 단일 객체 응답 대기
}