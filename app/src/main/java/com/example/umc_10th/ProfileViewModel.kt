package com.example.umc_10th

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.api.UserData
import com.example.umc_10th.data.repository.UserRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRemoteRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserData?>(null)
    val userProfile: StateFlow<UserData?> = _userProfile.asStateFlow()

    private val _followingList = MutableStateFlow<List<UserData>>(emptyList())
    val followingList: StateFlow<List<UserData>> = _followingList.asStateFlow()

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
        _userProfile.value = user
    }

    private suspend fun fetchFollowingList() {
        val users = repository.getUsers(1)
        val following = users.filter { it.id != 1 }
        _followingList.value = following
    }
}
