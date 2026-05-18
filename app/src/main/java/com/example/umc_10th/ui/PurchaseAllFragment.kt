package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.data.ProductDataStore
import com.example.umc_10th.databinding.FragmentPurchaseAllBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PurchaseAllFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseAllBinding
    private lateinit var purchaseAdapter: ProductAdapter

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

        purchaseAdapter = ProductAdapter(
            productList = mutableListOf(),
            onClicked = { /* TODO: 상세 페이지 이동 */ },
            onHeartClicked = { index, isLiked ->
                viewLifecycleOwner.lifecycleScope.launch {
                    ProductDataStore.updateProductLiked(requireContext(), index, isLiked)
                }
            }
        )
        binding.rcPurchaseAll.adapter = purchaseAdapter
        binding.rcPurchaseAll.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            ProductDataStore.getPurchaseProducts(requireContext()).collectLatest { products ->
                purchaseAdapter.updateList(products)
            }
        }
    }
}
