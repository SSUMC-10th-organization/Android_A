package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.FragmentPurchaseAllBinding
import com.example.umc_10th.ui.viewmodel.PurchaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseAllFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseAllBinding
    private val viewModel: PurchaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val purchaseAdapter = ProductAdapter(
            productList = mutableListOf(),
            onClicked = { /* TODO: 상세 페이지 이동 */ },
            onHeartClicked = { index, isLiked ->
                viewModel.updateLiked(index, isLiked)
            }
        )
        binding.rcPurchaseAll.adapter = purchaseAdapter
        binding.rcPurchaseAll.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.purchaseProducts.collect { products ->
                    purchaseAdapter.updateList(products)
                }
            }
        }
    }
}
