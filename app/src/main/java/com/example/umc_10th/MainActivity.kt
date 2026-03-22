package com.example.umc_10th

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_10th.fragment.CartFragment
import com.example.umc_10th.fragment.HomeFragment
import com.example.umc_10th.fragment.ProfileFragment
import com.example.umc_10th.fragment.ShopFragment
import com.example.umc_10th.fragment.WishlistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottom_nav)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)

        for (i in 0 until bottomNav.menu.size()) {
            val menuView = bottomNav.getChildAt(0) as ViewGroup
            val itemView = menuView.getChildAt(i) as ViewGroup
            for (j in 0 until itemView.childCount) {
                val view = itemView.getChildAt(j)
                if (view is TextView) {
                    view.typeface = typeface
                }
            }
        }

        // 초기 화면
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home       -> HomeFragment()
                R.id.nav_shop       -> ShopFragment()
                R.id.nav_wishlist   -> WishlistFragment()
                R.id.nav_cart       -> CartFragment()
                R.id.nav_profile    -> ProfileFragment()
                else -> return@setOnItemSelectedListener false
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            true
        }
    }
}