package com.example.umc_10th.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.umc_10th.fragment.ShopAllFragment
import com.example.umc_10th.fragment.ShopSaleFragment
import com.example.umc_10th.fragment.ShopTopsFragment

class ShopPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ShopAllFragment()
        1 -> ShopTopsFragment()
        2 -> ShopSaleFragment()
        else -> ShopAllFragment()
    }
}