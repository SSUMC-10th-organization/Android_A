package com.example.umc_10th.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.WishlistViewModel
import com.example.umc_10th.adapter.ProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WishlistViewModel by viewModels()
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
                viewModel.toggleFavorite(product.id, product.isFavorite)
            }
        )
        binding.rvWishlist.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.wishlistProducts.collect { products ->
                adapter.updateProducts(products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
