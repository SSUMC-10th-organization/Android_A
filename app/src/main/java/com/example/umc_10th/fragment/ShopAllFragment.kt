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
import com.example.umc_10th.databinding.FragmentShopAllBinding
import com.example.umc_10th.getProductsFlow
import com.example.umc_10th.initializeProductsIfEmpty
import com.example.umc_10th.updateProductFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopAllFragment : Fragment() {

    private var _binding: FragmentShopAllBinding? = null
    private val binding get() = _binding!!

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
                lifecycleScope.launch(Dispatchers.IO) {
                    updateProductFavorite(requireContext(), product.id, product.isFavorite)
                }
            }
        )
        binding.rvProducts.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            initializeProductsIfEmpty(requireContext())

            getProductsFlow(requireContext()).collect { products ->
                withContext(Dispatchers.Main) {
                    adapter.updateProducts(products)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}