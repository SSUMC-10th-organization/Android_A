package com.example.umc_10th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.WishlistFragmentBinding

class WishlistFragment: Fragment() {
    private lateinit var binding : WishlistFragmentBinding;
    private val consumeWishlistData = mutableListOf(
        ProductData(img = R.drawable.wishlist_img1, name = "Air Jordan 1 Mid", description = "", price = "US\$125"),
        ProductData(img = R.drawable.wishlist_img2, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
        ProductData(img = R.drawable.wishlist_img2, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
        ProductData(img = R.drawable.wishlist_img2, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WishlistFragmentBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wishlistAdapter = ProductAdapter( consumeWishlistData,
            onClicked = {
                Log.v("WishlistClick", "일단 클릭은 됨")
                val selectedProduct = DetailProductData(
                    category = "Training Crew Socks(위시리스트로부터 옴)",
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
        binding.rcWishlist.adapter = wishlistAdapter;
        binding.rcWishlist.layoutManager = GridLayoutManager(requireContext(), 2);
    }

}