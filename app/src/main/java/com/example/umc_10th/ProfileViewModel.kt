package com.example.umc_10th

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.api.UserData
import com.example.umc_10th.data.repository.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRemoteRepository
) : ViewModel() {

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
        val user = repository.getUser(1)
        _userProfile.postValue(user)
    }

    private suspend fun fetchFollowingList() {
        val users = repository.getUsers(1)
        val following = users.filter { it.id != 1 }
        _followingList.postValue(following)
    }
}
