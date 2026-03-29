package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.DummyData
import com.example.umc_10th.ProductAdapter
import com.example.umc_10th.R
import com.google.android.material.tabs.TabLayout

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_shop, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tab Font 설정
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.noto_sans_regular)

        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            val tabView = tab?.view
            for (j in 0 until (tabView?.childCount ?: 0)) {
                val child = tabView?.getChildAt(j)
                if (child is TextView) {
                    child.typeface = typeface
                }
            }
        }

        // RecyclerView 설정
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_products)
        val products = DummyData.getProducts()

        val adapter = ProductAdapter(
            products = products,
            onItemClick = { product ->
                val intent = android.content.Intent(requireContext(), com.example.umc_10th.ProductDetailActivity::class.java).apply {
                    putExtra("product_name", product.name)
                    putExtra("product_description", product.description)
                    putExtra("product_price", product.price)
                    putExtra("product_image", product.imageRes)
                    putExtra("product_favorite", product.isFavorite)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product, position ->
                val msg = if (product.isFavorite) "위시리스트에 추가됨" else "위시리스트에서 제거됨"
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }
}