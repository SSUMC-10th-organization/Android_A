package com.example.umc_10th.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.umc_10th.databinding.FragmentPurchaseBinding
import com.example.umc_10th.ui.product.AllFragment
import com.example.umc_10th.ui.purchase.SalesFragment
import com.example.umc_10th.ui.product.TopsTshirtsFragment
import com.google.android.material.tabs.TabLayoutMediator

class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. 탭을 움직이게 할 어댑터 연결
        val pagerAdapter = PurchasePagerAdapter(this)
        binding.purchaseViewPager.adapter = pagerAdapter

        // 2. TabLayout과 ViewPager2를 연결하고 이름 붙이기
        TabLayoutMediator(binding.purchaseTabLayout, binding.purchaseViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "전체"
                1 -> "Tops & T-Shirts"
                else -> "sale"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 3. 탭마다 어떤 프래그먼트를 보여줄지 결정하는 어댑터
    inner class PurchasePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AllFragment()
                1 -> TopsTshirtsFragment()
                else -> SalesFragment()
            }
        }
    }
}