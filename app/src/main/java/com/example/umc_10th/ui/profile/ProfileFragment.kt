package com.example.umc_10th.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_10th.R
import com.example.umc_10th.data.model.UserListResponse
import com.example.umc_10th.data.model.UserResponse
import com.example.umc_10th.data.remote.ReqResInterface
import com.example.umc_10th.domain.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    //사용할 어댑터를 변수로 미리 선언 (나중에 데이터가 도착했을 때 updateData를 호출하기 위해)
    @Inject
    lateinit var userRepository: UserRepository
    private lateinit var profileAdapter: ProfileAdapter

    //1. 전역 변수 및 뷰 생성 (onCreateView)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // 2. UI 초기화 (onViewCreated)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // XML에 정의된 rv_following을 찾아 어댑터와 연결
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_following)
        profileAdapter = ProfileAdapter(mutableListOf()) // 초기 빈 리스트
        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        //서버 데이터 요청 함수 호출
        fetchUserData(view)
    }

    //3. 서버 데이터 통신 (fetchUserData - Retrofit)
    private fun fetchUserData(view : View) {
        Log.d("UMC_DEBUG", "1. 함수 시작")
        //(1) 함수 실행 시 출력되는 로그

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ReqResInterface::class.java)
            //통신을 위해 Retrofit 객체를 함수 내부에서 직접 만듦


            //4. 팔로잉 리스트 업데이트
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    // [1] 내 프로필 정보 가져오기 (이미 성공하신 부분)
                    val myInfoResponse = apiService.getSingleUser(1)
                    val me = myInfoResponse.data

                    val nameTextView = view.findViewById<TextView>(R.id.tv_main_nickname)
                    val profileImageView = view.findViewById<ImageView>(R.id.iv_main_profile)

                    nameTextView?.text = "${me.first_name} ${me.last_name}"

                    Glide.with(requireContext())
                        .load(me.avatar)
                        .circleCrop()
                        .into(profileImageView)
                    // Glide로 상단 메인 이미지 세팅하는 코드도 여기에 있겠죠?
                    Log.d("TEST_LOG", "내 정보 로드 성공: ${me.first_name}")

                    // -----------------------------------------------------------
                    // [2] 팔로잉 목록 리스트 가져오기 (이드님이 주신 코드 통합)
                    // -----------------------------------------------------------
                    val response = apiService.getUserList(page = 1)
                    val userList = response.data

                    Log.d("TEST_LOG", "받아온 데이터 개수: ${userList.size}")

                    // 어댑터에 데이터 전달 (profileAdapter 이름 확인!)
                    profileAdapter.updateData(userList)
                    Log.d("TEST_LOG", "어댑터 업데이트 호출 완료")

                } catch (e: Exception) {
                    Log.e("ProfileFragment", "로딩 중 에러 발생: ${e.message}")
                }
            }

        } catch (e: Exception) {
            Log.e("UMC_DEBUG", "에러 발생: ${e.message}")
            //(4). 이외의 에러 발생 시 오류 코드와 함께 출력되는 로그
        }
    }
}