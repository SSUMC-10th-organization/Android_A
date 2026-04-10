package com.example.umc_10th

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_10th.adapter.ProfileAdapter
import com.example.umc_10th.data.WishlistStorage
import com.example.umc_10th.databinding.ActivityHomeBinding
import com.example.umc_10th.fragment.HomeFragment
import com.example.umc_10th.fragment.ProfileFragment
import com.example.umc_10th.fragment.PurchaseFragment
import com.example.umc_10th.fragment.ShoppingcartFragment
import com.example.umc_10th.fragment.WishlistFragment
import com.example.umc_10th.data.SharedPreferenceManager
import com.example.umc_10th.data.UserListResponse
import com.example.umc_10th.data.remote.ReqResInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //1. 전역 인스턴스 관리 (Companion Object)
    companion object {
        lateinit var wishlistStorage: WishlistStorage
        lateinit var prefManager: SharedPreferenceManager
    }
    //앱 어디서든 접근할 수 있는 공용 저장소 인스턴스를 선언
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. 초기 설정 및 데이터 로드 (onCreate)
        prefManager = SharedPreferenceManager(this)
        wishlistStorage = WishlistStorage(prefManager)
        wishlistStorage.loadFromDataStore()
        //객체 생성: 저장소 매니저와 관리자(Storage)를 메모리에 올림
        //loadFromDataStore()를 호출
        //이전에 저장된 위시리스트 데이터를 미리 불러옴 (탭 이동 시 딜레이 제거)

        setContentView(binding.root)

        //3. 화면 초기값 설정 (Fragment Transaction)
        if (savedInstanceState == null) {
        // 앱이 처음 실행될 때만 HomeFragment를 띄움. 화면 회전 시 프래그먼트가 중복 생성되는 것을 방지
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                //기존에 있던 화면을 떼어내고 HomeFragment로 교체
                .commitAllowingStateLoss()

        }

        // 4. 하단 탭 내비게이션 (BottomNavigationView)
        binding.mainBnv.setOnItemSelectedListener { item ->
            //사용자가 하단 바의 아이콘을 클릭할 때 발생하는 이벤트
            when (item.itemId) {
                // 클릭된 아이템의 ID에 맞춰 각각 다른 fragment를 main_frm(프레임 레이아웃) 자리에 끼워 넣음
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    true //선택됨 상태
                }
                R.id.purchaseFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ShoppingcartFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.wishlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WishlistFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.shoppingcartFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, PurchaseFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.profileFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ProfileFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }

        
    }

    //5. 외부 제어 함수 (changeFragment)
    fun changeFragment(index: Int) {
        when (index) {
            3 -> { // '구매' 탭으로 이동하고 싶을 때 (index 1로 가정)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, PurchaseFragment())
                    .commitAllowingStateLoss()

                //selectedItemId를 직접 수정해서 하단 바의 선택된 아이콘 위치도 강제로 옮김
                binding.mainBnv.selectedItemId = R.id.shoppingcartFragment
            }
        }
    }
    //특정 프래그먼트 내부에서 버튼을 눌러 다른 탭으로 이동하고 싶을 때 사용


}