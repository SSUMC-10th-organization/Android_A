package com.example.umc_10th.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.umc_10th.data.local.ProductDataStore
import com.example.umc_10th.data.model.ProductData
import com.example.umc_10th.databinding.FragmentWishlistBinding
import com.example.umc_10th.ui.purchase.PurchaseProductGrid
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private val viewModel: WishlistViewModel by viewModels()

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var productDataStore: ProductDataStore

    private var wishlistProducts by mutableStateOf<List<ProductData>>(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDataStore = ProductDataStore(requireContext())

        (binding.cvWishlist as ComposeView).setContent {
            PurchaseProductGrid(
                products = wishlistProducts,
                onLikeClick = { product ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        productDataStore.toggleLike(product.id)
                    }
                }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productDataStore.initializeIfEmpty()

            productDataStore.getProductsFlow().collect { productList ->
                wishlistProducts = productList.filter { it.isLiked }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}