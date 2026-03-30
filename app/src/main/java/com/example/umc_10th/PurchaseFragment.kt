package com.example.umc_10th

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_10th.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment() {
    private var _binding: FragmentPurchaseBinding? = null // 이름 주의!
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이 버튼이 들어있는 바로 그 프래그먼트에서 호출!
        binding.orderBtn.setOnClickListener {
            (activity as MainActivity).changeFragment(3)
        }
    }


}