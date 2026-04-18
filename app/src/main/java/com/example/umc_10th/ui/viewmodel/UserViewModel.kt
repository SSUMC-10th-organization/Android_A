package com.example.umc_10th.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_10th.data.model.UserData
import com.example.umc_10th.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository // 👈 Hilt가 Repository를 자동으로 넣어줍니다!
) : ViewModel() {

    // UI에서 관찰할 유저 리스트 상태
    private val _userList = MutableStateFlow<List<UserData>>(emptyList())
    val userList: StateFlow<List<UserData>> = _userList

    // 서버에서 유저 목록 가져오기
    fun fetchUsers(page: Int) {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserList(page)
                _userList.value = response.data
            } catch (e: Exception) {
                // 에러 처리 로직 (Toast 메시지용 LiveData 등을 추가해도 좋아요)
            }
        }
    }
}