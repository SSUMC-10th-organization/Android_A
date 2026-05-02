package com.example.umc_10th.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.ui.purchase.ProductAdapter
import com.example.umc_10th.data.local.ProductDataStore
import com.example.umc_10th.databinding.FragmentWishlistBinding
import kotlinx.coroutines.launch

import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private val viewModel: WishlistViewModel by viewModels()

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productDataStore: ProductDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
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

        binding.rvWishlist.adapter = productAdapter
        binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)

        viewLifecycleOwner.lifecycleScope.launch {
            productDataStore.initializeIfEmpty()

            productDataStore.getProductsFlow().collect { productList ->
                val wishlist = productList.filter { it.isLiked }
                productAdapter.submitList(wishlist)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}