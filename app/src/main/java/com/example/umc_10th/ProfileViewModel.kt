package com.example.umc_10th

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.api.ApiClient
import com.example.umc_10th.api.UserData
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<UserData?>()
    val userProfile: LiveData<UserData?> = _userProfile

    private val _followingList = MutableLiveData<List<UserData>>()
    val followingList: LiveData<List<UserData>> = _followingList

    // 중복 호출 방지
    private var isFetched = false

    fun prefetch() {
        if (isFetched) return
        isFetched = true

        viewModelScope.launch {
            fetchUserProfile()
            fetchFollowingList()
        }
    }

    private suspend fun fetchUserProfile() {
        try {
            val response = ApiClient.userService.getUser(1)
            if (response.isSuccessful) {
                _userProfile.postValue(response.body()?.data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchFollowingList() {
        try {
            val response = ApiClient.userService.getUsers(1)
            if (response.isSuccessful) {
                val following = response.body()?.data?.filter { it.id != 1 } ?: emptyList()
                _followingList.postValue(following)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}