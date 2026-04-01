package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.adapter.PurchaseAdapter
import com.example.umc_10th.data.PurchaseProduct
import com.example.umc_10th.R
import com.example.umc_10th.data.WishlistStorage
import com.example.umc_10th.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!

    // 1. 장바구니 전용 데이터 리스트 (PurchaseProduct 타입)
    private var purchaseList = mutableListOf<PurchaseProduct>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDummyData()
        initRecyclerView()
    }

    private fun initDummyData() {
        purchaseList.clear() // 기존 리스트 비우기

        // 1. rawData 변수 선언 (이 변수는 이 함수 안에서만 유효합니다)
        val rawData = listOf(
            PurchaseProduct(
                R.drawable.socks1,
                "Nike Everyday Plus\nCushioned",
                "Training Ankle Socks (6 Pairs)\n5 Colours",
                "US$20"
            ),
            PurchaseProduct(
                R.drawable.socks2,
                "Nike Elite Crew",
                "Basketball Crew Socks\n3 Colours",
                "US$160"
            ),
            PurchaseProduct(
                R.drawable.women_shoes,
                "Nike Air Force 1 '07",
                "Classic Design",
                "US$115"
            ),
            PurchaseProduct(R.drawable.men_shoes, "Jordan Essentials", "Comfortable Fit", "US$35"),
            PurchaseProduct(
                R.drawable.socks1,
                "Nike Spark Lightweight",
                "Breathable Fabric",
                "US$18"
            ),
            PurchaseProduct(R.drawable.socks2, "Nike Multiplier", "Performance Socks", "US$22"),
            PurchaseProduct(R.drawable.women_shoes, "Nike Air Max Pro", "Air Cushioning", "US$180"),
            PurchaseProduct(R.drawable.men_shoes, "Nike Pegasus 40", "Running Shoes", "US$130")
        )

        // 2. 위에서 만든 rawData를 사용하여 purchaseList에 추가
        rawData.forEach { product ->
            // WishlistStorage에 이미 있는 상품인지 확인해서 하트 상태 결정
            product.isFavorite = WishlistStorage.isFavorite(product.name!!)
            purchaseList.add(product)
        }
    }


    private fun initRecyclerView() {
        // 2. 전용 어댑터 연결 (아이템 클릭, 하트 클릭 2개의 콜백 전달)
        val purchaseAdapter = PurchaseAdapter(
            itemList = purchaseList,
            onItemClicked = { product ->
                // 상품 자체를 클릭했을 때의 동작 (필요 시 작성)
            },
            onHeartClicked = { product ->
                // 3. 하트 상태에 따라 WishlistStorage(데이터 저장소)에 넣거나 빼기
                if (product.isFavorite) {
                    WishlistStorage.addProduct(product)
                } else {
                    WishlistStorage.removeProduct(product)
                }
            }
        )

        binding.purchaseRecyclerView.apply {
            adapter = purchaseAdapter
            // 2열 격자 레이아웃 설정
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}