package com.example.umc_10th.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.DummyData
import com.example.umc_10th.HomeProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_home_products)
        val allProducts = DummyData.getProducts()
        val products = listOf(
            allProducts.first { it.id == 5 },  // Air Jordan XXXVI
            allProducts.first { it.id == 3 }   // Nike Air Force 1 '07
        )

        val adapter = HomeProductAdapter(products) { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("product_name", product.name)
                putExtra("product_description", product.description)
                putExtra("product_price", product.price)
                putExtra("product_image", product.imageRes)
                putExtra("product_favorite", product.isFavorite)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.adapter = adapter
    }
}