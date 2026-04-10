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

    //사용할 어댑터를 변수로 미리 선언 (나중에 데이터가 도착했을 때 updateData를 호출하기 위해)
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
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_following)
        profileAdapter = ProfileAdapter(listOf()) // 초기 빈 리스트
        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        //서버 데이터 요청 함수 호출
        fetchUserData()
    }

    //3. 서버 데이터 통신 (fetchUserData - Retrofit)
    private fun fetchUserData() {
        Log.d("UMC_DEBUG", "1. 함수 시작")
        //(1) 함수 실행 시 출력되는 로그

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ReqResInterface::class.java)
            //통신을 위해 Retrofit 객체를 함수 내부에서 직접 만듦


            //4. 팔로잉 리스트 업데이트 (getUserList)
            apiService.getUserList(1).enqueue(object : Callback<UserListResponse> {
                //비동기 요청 : enqueue를 통해 서버에 목록 요청 후 대기

                override fun onResponse(call: Call<UserListResponse>, response: Response<UserListResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.data?.let { profileAdapter.updateData(it) }
                        Log.d("UMC_DEBUG", "리스트 데이터 로드 성공")
                        //(2)-1. 데이터 로드 성공 시 출력되는 로그
                    }
                }
                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    Log.e("UMC_DEBUG", "리스트 통신 실패: ${t.message}")
                    //(2)-2. 데이터 로드 실패 시 오류 코드와 함께 출력되는 로그
                }
            })

            //5. 내 프로필 UI 업데이트 (getSingleUser)
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
                            //서버에서 준 이름과 성을 합쳐서 닉네임 텍스트뷰에 추가

                            // 2. 상단 메인 프로필 이미지 변경 (Glide 활용)
                            val profileImageView = view?.findViewById<android.widget.ImageView>(R.id.iv_main_profile)
                            if (profileImageView != null) {
                                com.bumptech.glide.Glide.with(requireContext())
                                    .load(it.avatar)
                                    .circleCrop()
                                    .into(profileImageView)
                                //프로필 이미지 URL을 가져와서 circleCrop()으로 동그랗게 깎아 메인 이미지뷰에 넣음
                            }
                            Log.d("UMC_DEBUG", "내 정보(1번 유저) 로드 성공: ${it.first_name}")
                            //(3)-1. 유저 데이터 로드 성공 시 출력되는 로그
                        }
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("UMC_DEBUG", "내 정보 통신 실패: ${t.message}")
                    //(3)-2. 유저 데이터 로드 실패 시 오류 코드와 함께 출력되는 로그
                }
            })

        } catch (e: Exception) {
            Log.e("UMC_DEBUG", "에러 발생: ${e.message}")
            //(4). 이외의 에러 발생 시 오류 코드와 함께 출력되는 로그
        }
    }
}

// * 통신이 enqueue를 통해 비동기적으로 일어나므로 데이터가 로드되기 전까지는 빈 화면 출력
/* * Glide.with(requireContext())처럼 fragment에서
     Context가 필요할 때는 안전하게 requireContext()를 사용한다는 점이 중요 */