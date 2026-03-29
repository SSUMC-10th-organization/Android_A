package com.example.umc_10th.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.DummyData
import com.example.umc_10th.ProductAdapter
import com.example.umc_10th.ProductDetailActivity
import com.example.umc_10th.R

class WishlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_wishlist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_wishlist)

        // 위시리스트용 더미 데이터 (isFavorite = true인 것만)
        val wishlistProducts = DummyData.getProducts().filter { it.isFavorite }.toMutableList()

        val adapter = ProductAdapter(
            products = wishlistProducts,
            onItemClick = { product ->
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("product_name", product.name)
                    putExtra("product_description", product.description)
                    putExtra("product_price", product.price)
                    putExtra("product_image", product.imageRes)
                    putExtra("product_favorite", product.isFavorite)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product, position -> }
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }
}