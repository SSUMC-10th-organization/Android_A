package com.example.umc_10th.ui.purchase

import androidx.fragment.app.Fragment
import com.example.umc_10th.R

import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class SaleFragment : Fragment(R.layout.fragment_sale){
    private val viewModel: PurchaseViewModel by viewModels()

}