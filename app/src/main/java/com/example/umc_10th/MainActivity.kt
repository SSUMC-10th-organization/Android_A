package com.example.umc_10th

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_10th.databinding.ActivityMainBinding
import com.example.umc_10th.fragment.CartFragment
import com.example.umc_10th.fragment.HomeFragment
import com.example.umc_10th.fragment.ProfileFragment
import com.example.umc_10th.fragment.ShopFragment
import com.example.umc_10th.fragment.WishlistFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        val typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)

        for (i in 0 until binding.bottomNav.menu.size()) {
            val menuView = binding.bottomNav.getChildAt(0) as ViewGroup
            val itemView = menuView.getChildAt(i) as ViewGroup
            for (j in 0 until itemView.childCount) {
                val view = itemView.getChildAt(j)
                if (view is TextView) {
                    view.typeface = typeface
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        binding.bottomNav.setOnItemSelectedListener { item ->
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

    fun navigateToShop() {
        binding.bottomNav.selectedItemId = R.id.nav_shop
    }
}