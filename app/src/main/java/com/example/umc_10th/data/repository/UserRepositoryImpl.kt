package com.example.umc_10th.data.repository

import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.model.UserData
import com.example.umc_10th.data.model.UserListResponse
import com.example.umc_10th.data.remote.ReqResInterface
import com.example.umc_10th.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.lang.reflect.Type
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ReqResInterface,
    private val sharedPref: SharedPreferenceManager

) : UserRepository {
    override suspend fun getUserList(page: Int): UserListResponse {
        return api.getUserList(page)
    }

    override suspend fun insertUser(user: UserData) {
        // 기존에 저장된 리스트를 가져와서 새 유저를 추가한 뒤 다시 저장하는 로직이 필요할 수 있습니다.
        // 여기서는 간단하게 이드님이 만든 saveObjectList를 활용하는 예시입니다.
        // 필요하다면 SharedPreferenceManager에 KEY_USER_DATA를 추가해서 사용하세요!
        val currentUsers = getAllUsers()
        val updatedList = currentUsers + user

        // 유저 저장을 위한 키가 필요하므로 임시로 KEY_WISHLIST 등을 활용하거나 새 키를 정의해야 합니다.
        // dataStoreManager.saveObjectList(SharedPreferenceManager.KEY_USER_DATA, updatedList)
    }

    // 3. 로컬 DataStore에서 모든 유저 가져오기
    override suspend fun getAllUsers(): List<UserData> {
        // List<UserData> 타입을 GSON이 인식할 수 있도록 토큰 생성
        val typeToken: Type = object : com.google.gson.reflect.TypeToken<List<UserData>>() {}.type

        // getObjectList는 Flow를 반환하므로, .first()를 사용해 현재 저장된 리스트를 가져옵니다.
        // kotlinx.coroutines.flow.first 를 import 해주세요!
        return sharedPref.getObjectList<UserData>(
            SharedPreferenceManager.KEY_USER_LIST,
            typeToken
        ).first()
    }

}