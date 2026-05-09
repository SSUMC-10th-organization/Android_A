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
import com.example.umc_10th.ShopAllViewModel
import com.example.umc_10th.adapter.ProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.databinding.FragmentShopAllBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopAllFragment : Fragment() {

    private var _binding: FragmentShopAllBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShopAllViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

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
            onFavoriteClick = { product, _ ->
                viewModel.toggleFavorite(product.id, product.isFavorite)
            }
        )
        binding.rvProducts.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collect { products ->
                adapter.updateProducts(products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
