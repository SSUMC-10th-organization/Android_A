package com.example.umc_10th.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_10th.R
import com.example.umc_10th.adapter.ProfileAdapter
import com.example.umc_10th.data.UserListResponse
import com.example.umc_10th.data.remote.ReqResInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.data.UserResponse

class ProfileFragment : Fragment() {

    // 1. 사용할 어댑터를 변수로 미리 선언 (나중에 데이터 넣어줘야 하니까!)
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // XML 파일명 확인 (fragment_profile인지 fragment_mypage인지 확인해봐!)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // 2. 뷰가 생성된 직후에 실행되는 함수 (여기서 로직을 짜야 해!)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리사이클러뷰와 어댑터 연결 (아이디는 본인 XML 확인!)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_following)
        profileAdapter = ProfileAdapter(listOf()) // 초기 빈 리스트
        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // 3. 서버 데이터 요청 함수 호출
        fetchUserData()
    }

    private fun fetchUserData() {
        Log.d("UMC_DEBUG", "1. 함수 시작")

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ReqResInterface::class.java)

            // --- [기존] 3번 항목: 팔로잉 리스트(여러 명) 가져오기 ---
            apiService.getUserList(1).enqueue(object : Callback<UserListResponse> {
                override fun onResponse(call: Call<UserListResponse>, response: Response<UserListResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let { profileAdapter.updateData(it) }
                        Log.d("UMC_DEBUG", "리스트 데이터 로드 성공")
                    }
                }
                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    Log.e("UMC_DEBUG", "리스트 통신 실패: ${t.message}")
                }
            })

            // --- [추가] 2번 항목: 내 정보(1번 유저) 가져오기 ---
            apiService.getSingleUser(1).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val user = response.body()?.data
                        user?.let {
                            // 1. 상단 닉네임 변경 (First + Last Name)
                            // binding 사용 시: binding.tvNickname.text = "${it.first_name} ${it.last_name}"
                            // findViewById 사용 시 예시:
                            val nameTextView = view?.findViewById<android.widget.TextView>(R.id.tv_main_nickname)
                            nameTextView?.text = "${it.first_name} ${it.last_name}"

                            // 2. 상단 메인 프로필 이미지 변경 (Glide 활용)
                            val profileImageView = view?.findViewById<android.widget.ImageView>(R.id.iv_main_profile)
                            if (profileImageView != null) {
                                com.bumptech.glide.Glide.with(requireContext())
                                    .load(it.avatar)
                                    .circleCrop() // 동그랗게!
                                    .into(profileImageView)
                            }
                            Log.d("UMC_DEBUG", "내 정보(1번 유저) 로드 성공: ${it.first_name}")
                        }
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("UMC_DEBUG", "내 정보 통신 실패: ${t.message}")
                }
            })

        } catch (e: Exception) {
            Log.e("UMC_DEBUG", "에러 발생: ${e.message}")
        }
    }
}