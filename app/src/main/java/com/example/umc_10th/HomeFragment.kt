package com.example.umc_10th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.databinding.HomeFragmentBinding

class HomeFragment: Fragment() {
    private lateinit var binding: HomeFragmentBinding;
    private val consumeHomeData = mutableListOf(
        ProductData(img = R.drawable.home_img1, name = "Air Jordan XXXVI", price = "US\$185"), // 0번
        ProductData(img = R.drawable.home_img2, name = "Nike Air Force 1 '07", price = "US\$115") // 1번
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wishlistAdapter = ProductAdapter( consumeHomeData,
            onClicked = {
                Log.v("WishlistClick", "일단 클릭은 됨")
                val selectedProduct = DetailProductData(
                    category = "Training Crew Socks(홈으로부터 옴)",
                    name = "Nike Everyday Plus Cushioned",
                    price = "US$10",
                    description = "The Nike Everyday Plus Cushioned Socks bring comfort to your workout...",
                    imageRes = R.drawable.wishlist_img1,
                    styleCode = "SX6897-965",
                    color = "Multi-Color"
                )
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra("detail_info", selectedProduct);
                }
                startActivity(intent);
            })
        binding.rcHome.adapter = wishlistAdapter;
        binding.rcHome.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);

    }




}