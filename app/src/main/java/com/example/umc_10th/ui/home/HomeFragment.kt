package com.example.umc_10th.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.umc_10th.data.local.ProductDataStore
import com.example.umc_10th.data.model.ProductData
import com.example.umc_10th.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productDataStore: ProductDataStore

    private var homeProducts by mutableStateOf<List<ProductData>>(emptyList())

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

        binding.cvHomeProduct.setContent {
            HomeProductLazyRow(
                products = homeProducts
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productDataStore.initializeIfEmpty()

            productDataStore.getProductsFlow().collect { productList ->
                homeProducts = productList.takeLast(2)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}