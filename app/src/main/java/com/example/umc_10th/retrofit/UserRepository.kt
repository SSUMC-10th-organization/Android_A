package com.example.umc_10th.retrofit

class UserRepository(
    private val service: UserService
) {
    suspend fun getUser(id: Int): Result<UserData> {
        return try {
            val response = service.getUser(
                id = id,
                apiKey = ReqresConfig.API_KEY
            )

            if (response.isSuccessful) {
                val body = response.body()

                if (body == null) {
                    Result.failure(Exception("응답 body가 null입니다."))
                } else {
                    Result.success(body.data)
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Result.failure(Exception("HTTP ${response.code()} : $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserList(page: Int): Result<List<UserData>> {
        return try {
            val response = service.getUserList(
                page = page,
                apiKey = ReqresConfig.API_KEY
            )

            if (response.isSuccessful) {
                val body = response.body()

                if (body == null) {
                    Result.failure(Exception("응답 body가 null입니다."))
                } else {
                    Result.success(body.data)
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: response.message()
                Result.failure(Exception("HTTP ${response.code()} : $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}