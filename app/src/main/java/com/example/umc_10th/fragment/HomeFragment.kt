package com.example.umc_10th.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.HomeProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.R
import com.example.umc_10th.getProductsFlow
import com.example.umc_10th.initializeProductsIfEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_home_products)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        lifecycleScope.launch(Dispatchers.IO) {
            // 최초 진입 시 더미 데이터를 DataStore에 저장
            initializeProductsIfEmpty(requireContext())

            getProductsFlow(requireContext()).collect { allProducts ->
                val homeProducts = listOf(
                    allProducts.firstOrNull { it.id == 5 },
                    allProducts.firstOrNull { it.id == 3 }
                ).filterNotNull()

                withContext(Dispatchers.Main) {
                    recyclerView.adapter = HomeProductAdapter(homeProducts) { product ->
                        val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                            putExtra("product_id", product.id)
                            putExtra("product_name", product.name)
                            putExtra("product_description", product.description)
                            putExtra("product_price", product.price)
                            putExtra("product_image", product.imageRes)
                            putExtra("product_favorite", product.isFavorite)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
