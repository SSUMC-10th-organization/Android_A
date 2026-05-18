package com.example.umc_10th.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.R
import com.example.umc_10th.data.ProductDataStore
import com.example.umc_10th.databinding.WishlistFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {
    private lateinit var binding: WishlistFragmentBinding

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

        // DataStore에서 liked=true인 상품만 필터링하여 표시
        viewLifecycleOwner.lifecycleScope.launch {
            ProductDataStore.getPurchaseProducts(requireContext())
                .map { products -> products.filter { it.isLiked } }
                .collectLatest { likedProducts ->
                    wishlistAdapter.updateList(likedProducts)
                }
        }
    }
}
