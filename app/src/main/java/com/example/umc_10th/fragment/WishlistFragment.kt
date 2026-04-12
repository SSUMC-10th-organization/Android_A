package com.example.umc_10th.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.adapter.ProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.databinding.FragmentWishlistBinding
import com.example.umc_10th.getProductsFlow
import com.example.umc_10th.initializeProductsIfEmpty
import com.example.umc_10th.updateProductFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductAdapter

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

        binding.rvWishlist.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = ProductAdapter(
            products = mutableListOf(),
            onItemClick = { product ->
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("product_id", product.id)
                    putExtra("product_name", product.name)
                    putExtra("product_description", product.description)
                    putExtra("product_price", product.price)
                    putExtra("product_image", product.imageRes)
                    putExtra("product_favorite", product.isFavorite)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product, position ->
                // 하트 해제 시 즉시 리스트에서 제거
                adapter.removeAt(position)
                lifecycleScope.launch(Dispatchers.IO) {
                    updateProductFavorite(requireContext(), product.id, product.isFavorite)
                }
            }
        )
        binding.rvWishlist.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            initializeProductsIfEmpty(requireContext())

            getProductsFlow(requireContext()).collect { allProducts ->
                val wishlistProducts = allProducts.filter { it.isFavorite }
                withContext(Dispatchers.Main) {
                    adapter.updateProducts(wishlistProducts)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}