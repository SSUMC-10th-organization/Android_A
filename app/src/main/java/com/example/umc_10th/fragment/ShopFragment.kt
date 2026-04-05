package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_10th.R
import com.example.umc_10th.ShopPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_shop, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

        viewPager.adapter = ShopPagerAdapter(requireActivity())

        val tabTitles = listOf("전체", "Tops & T-Shirts", "Sale")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // Tab Font 설정
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.noto_sans_regular)
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            val tabView = tab?.view
            for (j in 0 until (tabView?.childCount ?: 0)) {
                val child = tabView?.getChildAt(j)
                if (child is TextView) {
                    child.typeface = typeface
                }
            }
        }
    }
}