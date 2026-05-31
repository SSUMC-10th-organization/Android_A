package com.example.umc_10th.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.retrofit.ApiClient
import com.example.umc_10th.retrofit.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val repository = UserRepository(ApiClient.userService)

    private val _uiState = MutableStateFlow(ProfileUiState(isLoading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val userResult = repository.getUser(id = 1)
            val listResult = repository.getUserList(page = 1)

            val currentUser = userResult.getOrNull()

            val followingUsers = listResult.getOrNull()
                ?.filter { it.id != 1 }
                ?.take(3)
                ?: emptyList()

            if (userResult.isSuccess) {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    user = currentUser,
                    followingUsers = followingUsers,
                    errorMessage = null
                )
            } else {
                _uiState.value = ProfileUiState(
                    isLoading = false,
                    user = null,
                    followingUsers = emptyList(),
                    errorMessage = userResult.exceptionOrNull()?.message
                        ?: "프로필 정보를 불러오지 못했습니다."
                )
            }
        }
    }
}