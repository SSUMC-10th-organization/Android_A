package com.example.umc_10th.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.R
import com.example.umc_10th.databinding.HomeFragmentBinding
import com.example.umc_10th.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()

    private val dummyHomeProducts = listOf(
        ProductData(img = R.drawable.air_force_image, name = "Air Jordan XXXVI", price = "US\$185"),
        ProductData(img = R.drawable.jordan_image, name = "Nike Air Force 1 '07", price = "US\$115")
    )

    private val dummyPurchaseProducts = listOf(
        ProductData(img = R.drawable.socks_image, name = "Air Jordan 1 Mid", description = "", price = "US\$125"),
        ProductData(img = R.drawable.white_shoe_image, name = "Nike Elite Crew", description = "Basketball Socks\n7 Colours", price = "US\$16"),
        ProductData(img = R.drawable.socks_image, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
        ProductData(img = R.drawable.white_shoe_image, name = "Nike Everyday Plus Cushioned 2", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = ProductAdapter(
            productList = mutableListOf(),
            onClicked = {
                val selectedProduct = DetailProductData(
                    category = "Training Crew Socks(홈으로부터 옴)",
                    name = "Nike Everyday Plus Cushioned",
                    price = "US$10",
                    description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout...",
                    imageRes = R.drawable.socks_image,
                    styleCode = "SX6897-965",
                    color = "Multi-Color"
                )
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("detail_info", selectedProduct)
                }
                startActivity(intent)
            }
        )
        binding.rcHome.adapter = homeAdapter
        binding.rcHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.initializeIfEmpty(dummyHomeProducts, dummyPurchaseProducts)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeProducts.collect { products ->
                    homeAdapter.updateList(products)
                }
            }
        }
    }
}
