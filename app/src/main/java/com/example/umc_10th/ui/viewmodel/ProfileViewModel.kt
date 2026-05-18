package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.repository.UserRemoteRepository
import com.example.umc_10th.network.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val nickname: String = "",
    val avatarUrl: String = "",
    val followingList: List<UserData> = emptyList()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteRepository: UserRemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val user = remoteRepository.getUser(1)
                val followingList = remoteRepository.getUserList(1)
                _uiState.value = ProfileUiState(
                    nickname = "${user.first_name} ${user.last_name}",
                    avatarUrl = user.avatar,
                    followingList = followingList
                )
            } catch (e: Exception) {
                // 네트워크 오류 시 기본값 유지
            }
        }
    }
}
