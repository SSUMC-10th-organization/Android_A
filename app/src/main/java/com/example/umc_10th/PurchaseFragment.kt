package com.example.umc_10th

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.umc_10th.databinding.FragmentPurchaseBinding
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment(R.layout.fragment_purchase) {

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!

    private val tabTitles = listOf("전체", "Top & T-shirts", "Sale")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPurchaseBinding.bind(view)

        binding.viewPager.adapter = PurchaseTabPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}