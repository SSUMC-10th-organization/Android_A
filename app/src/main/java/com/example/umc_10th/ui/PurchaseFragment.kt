package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_10th.databinding.PurchaseFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment() {
    private lateinit var binding: PurchaseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchaseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PurchasePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "전체"
                1 -> "Tops & T-shirts"
                else -> "Sale"
            }
        }.attach()
    }
}
