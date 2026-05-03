package com.example.umc_10th.ui.purchase

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
import com.example.umc_10th.ui.purchase.ProductAdapter
import com.example.umc_10th.databinding.FragmentAllPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllPurchaseFragment : Fragment() {

    private var _binding: FragmentAllPurchaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter

    private val purchaseViewModel: PurchaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(
            productList = mutableListOf(),
            onLikeClicked = { product ->
                purchaseViewModel.toggleLike(product.id)
            }
        )

        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                purchaseViewModel.uiState.collect { state ->
                    productAdapter.submitList(state.products)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}