package com.example.umc_10th.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_10th.R
import com.example.umc_10th.databinding.FragmentCartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val viewModel: CartViewModel by viewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.btnOrder.setOnClickListener {
            requireActivity()
                .findViewById<BottomNavigationView>(R.id.main_bnv)
                .selectedItemId = R.id.purchaseFragment
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}