package com.example.umc_10th

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_10th.databinding.ActivityMainBinding
import com.example.umc_10th.ui.cart.CartFragment
import com.example.umc_10th.ui.home.HomeFragment
import com.example.umc_10th.ui.profile.ProfileFragment
import com.example.umc_10th.ui.purchase.PurchaseFragment
import com.example.umc_10th.ui.wishlist.WishlistFragment
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, HomeFragment())
            .commit()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, HomeFragment())
                        .commit()
                    true
                }

                R.id.purchaseFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, PurchaseFragment())
                        .commit()
                    true
                }

                R.id.wishlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, WishlistFragment())
                        .commit()
                    true
                }

                R.id.cartFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, CartFragment())
                        .commit()
                    true
                }

                R.id.profileFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.mainFragmentContainer.id, ProfileFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}