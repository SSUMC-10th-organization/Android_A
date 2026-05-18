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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.R
import com.example.umc_10th.databinding.WishlistFragmentBinding
import com.example.umc_10th.ui.viewmodel.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private lateinit var binding: WishlistFragmentBinding
    private val viewModel: WishlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WishlistFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wishlistAdapter = ProductAdapter(
            productList = mutableListOf(),
            onClicked = {
                val selectedProduct = DetailProductData(
                    category = "Training Crew Socks(위시리스트로부터 옴)",
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
        binding.rcWishlist.adapter = wishlistAdapter
        binding.rcWishlist.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.likedProducts.collect { products ->
                    wishlistAdapter.updateList(products)
                }
            }
        }
    }
}
