package com.example.umc_10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.FragmentAllPurchaseBinding
import kotlinx.coroutines.launch

class AllPurchaseFragment : Fragment() {

    private var _binding: FragmentAllPurchaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productDataStore: ProductDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDataStore = ProductDataStore(requireContext())

        productAdapter = ProductAdapter(
            productList = mutableListOf(),
            onLikeClicked = { product ->
                viewLifecycleOwner.lifecycleScope.launch {
                    productDataStore.toggleLike(product.id)
                }
            }
        )

        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            productDataStore.initializeIfEmpty()
            productDataStore.getProductsFlow().collect { productList ->
                productAdapter.submitList(productList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}