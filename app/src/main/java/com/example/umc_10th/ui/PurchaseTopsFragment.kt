package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_10th.databinding.FragmentPurchaseTopsBinding

class PurchaseTopsFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseTopsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseTopsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
