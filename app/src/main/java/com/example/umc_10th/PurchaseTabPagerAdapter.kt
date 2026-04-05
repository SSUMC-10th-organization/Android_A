package com.example.umc_10th

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PurchaseTabPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllPurchaseFragment()
            1 -> TopTshirtsFragment()
            2 -> SaleFragment()
            else -> AllPurchaseFragment()
        }
    }
}