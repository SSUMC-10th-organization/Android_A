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

    // 1. 팔로잉 리스트 상태
    private val _userList = MutableStateFlow<List<UserData>>(emptyList())
    val userList: StateFlow<List<UserData>> = _userList

    // 2. 내 정보 상태
    private val _myInfo = MutableStateFlow<UserData?>(null)
    val myInfo: StateFlow<UserData?> = _myInfo

    // 서버에서 데이터 로드
    fun fetchProfileData(page: Int, myId: Int) {
        viewModelScope.launch {
            try {
                // 팔로잉 목록 가져오기
                val listResponse = userRepository.getUserList(page)
                _userList.value = listResponse.data

                // 내 정보 가져오기 (Repository에 getSingleUser가 있다고 가정하거나,
                // 임시로 리스트의 첫 번째 유저를 사용)
                // 여기서는 리스트의 첫 번째 유저를 '나'라고 가정하거나
                // 기존 Fragment 로직처럼 id 1번을 가져오는 로직을 추가할 수 있습니다.
                _myInfo.value = listResponse.data.find { it.id == myId } ?: listResponse.data.firstOrNull()
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}