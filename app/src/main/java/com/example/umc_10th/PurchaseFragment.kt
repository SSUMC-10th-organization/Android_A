package com.example.umc_10th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.PurchaseFragmentBinding

class PurchaseFragment: Fragment() {
    private lateinit var binding : PurchaseFragmentBinding;

    private val consumePurchaseData = mutableListOf(
        ProductData(img = R.drawable.wishlist_img1, name = "Air Jordan 1 Mid", description = "", price = "US\$125"),
        ProductData(img = R.drawable.wishlist_img2, name = "Nike Elite Crew", description = "Basketball Socks\n7 Colours", price = "US\$16"),
        ProductData(img = R.drawable.wishlist_img1, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
        ProductData(img = R.drawable.wishlist_img2, name = "Nike Everyday Plus Cushioned", description = "Training Ankle Socks (6 Pairs)\n5 Colours", price = "US\$10"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PurchaseFragmentBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val purchaseAdapter = ProductAdapter(consumePurchaseData,
            onClicked = {
//                TODO: 클릭 이벤트 구현
//                얘는 상세 페이지로 안 넘어가는 거 봐선 뭔가
//                다른 로직으로 클릭이벤트 처리하나봄
                Log.v("PurchaseClick", "일단 클릭은 됨")
            }
        )
        binding.rcPurchase.adapter = purchaseAdapter
        binding.rcPurchase.layoutManager = GridLayoutManager(requireContext(), 2);
    }

}