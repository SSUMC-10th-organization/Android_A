package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.umc_10th.R
import com.example.umc_10th.ShopPagerAdapter
import com.example.umc_10th.databinding.FragmentShopBinding
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ShopPagerAdapter(requireActivity())

        val tabTitles = listOf("전체", "Tops & T-Shirts", "Sale")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // Tab Font 설정
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.noto_sans_regular)
        for (i in 0 until binding.tabLayout.tabCount) {
            val tab = binding.tabLayout.getTabAt(i)
            val tabView = tab?.view
            for (j in 0 until (tabView?.childCount ?: 0)) {
                val child = tabView?.getChildAt(j)
                if (child is TextView) {
                    child.typeface = typeface
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}