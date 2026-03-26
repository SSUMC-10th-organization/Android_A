package com.example.umc_10th

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_10th.databinding.ActivityHomeBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

// 1. [수정] 시작 화면도 반드시 main_frm에 넣어줘야 합니다!
        if (savedInstanceState == null) { // 앱 처음 실행 시에만 프래그먼트 생성
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment()) // R.id.main -> R.id.main_frm
                .commitAllowingStateLoss()
        }

        // 2. 하단 탭 클릭 시 Fragment 전환 설정
        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // home_menu.xml에 적은 id와 아래 R.id.xxx 이름이 같아야 합니다!
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.purchaseFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, PurchaseFragment())
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
                        .replace(R.id.main_frm, ShoppingcartFragment())
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
    fun changeFragment(index: Int) {
        when (index) {
            3 -> { // '구매' 탭으로 이동하고 싶을 때 (index 1로 가정)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShoppingcartFragment())
                    .commitAllowingStateLoss()

                // 핵심: 하단 바의 아이콘도 '구매'로 변경 (ID는 본인의 menu/home_menu.xml 확인)
                binding.mainBnv.selectedItemId = R.id.shoppingcartFragment
            }
            // 필요하다면 다른 번호를 추가해서 다른 화면으로도 보낼 수 있어요!
        }
    }
}