package com.example.umc_10th.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.data.local.ProductDataStore
import com.example.umc_10th.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeProductAdapter: HomeProductAdapter
    private lateinit var productDataStore: ProductDataStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDataStore = ProductDataStore(requireContext())
        homeProductAdapter = HomeProductAdapter(mutableListOf())

        binding.rvHomeProduct.apply {
            adapter = homeProductAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productDataStore.initializeIfEmpty()

            productDataStore.getProductsFlow().collect { productList ->
                val latestProducts = productList.takeLast(2)
                homeProductAdapter.submitList(latestProducts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}