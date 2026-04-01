package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.MainActivity
import com.example.umc_10th.adapter.PurchaseAdapter
import com.example.umc_10th.data.PurchaseProduct
import com.example.umc_10th.R
import com.example.umc_10th.data.SharedPreferenceManager
import com.example.umc_10th.databinding.FragmentPurchaseBinding
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!

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
        val type = object : TypeToken<List<PurchaseProduct>>() {}.type

        viewLifecycleOwner.lifecycleScope.launch {
            // 1. DataStore에서 먼저 읽어오기
            val savedList = MainActivity.prefManager.getObjectList<PurchaseProduct>(
                SharedPreferenceManager.KEY_PURCHASE_PRODUCTS, type
            ).first()

            if (savedList.isEmpty()) {
                // 2. 저장된 게 없다면 새로 만들기
                val dummy = mutableListOf(
                    PurchaseProduct(R.drawable.socks1, "Nike Everyday Plus\nCushioned", "Training Ankle Socks (6 Pairs)\n5 Colours", "US$20"),
                    PurchaseProduct(R.drawable.socks2, "Nike Elite Crew", "Basketball Crew Socks\n3 Colours", "US$160"),
                    PurchaseProduct(R.drawable.women_shoes, "Nike Air Force 1 '07", "Classic Design", "US$115"),
                    PurchaseProduct(R.drawable.men_shoes, "Jordan Essentials", "Comfortable Fit", "US$35"),
                    PurchaseProduct(R.drawable.socks1, "Nike Spark Lightweight", "Breathable Fabric", "US$18"),
                    PurchaseProduct(R.drawable.socks2, "Nike Multiplier", "Performance Socks", "US$22"),
                    PurchaseProduct(R.drawable.women_shoes, "Nike Air Max Pro", "Air Cushioning", "US$180"),
                    PurchaseProduct(R.drawable.men_shoes, "Nike Pegasus 40", "Running Shoes", "US$130")
                )

                // 하트 상태 반영
                dummy.forEach { product ->
                    product.isFavorite = MainActivity.wishlistStorage.isFavorite(product.name!!)
                }

                purchaseList.clear()
                purchaseList.addAll(dummy)

                // DataStore에 첫 저장
                MainActivity.prefManager.saveObjectList(
                    SharedPreferenceManager.KEY_PURCHASE_PRODUCTS,
                    dummy
                )
            } else {
                // 3. 이미 저장된 데이터가 있다면 하트 상태만 업데이트해서 불러오기
                savedList.forEach { product ->
                    product.isFavorite = MainActivity.wishlistStorage.isFavorite(product.name!!)
                }
                purchaseList.clear()
                purchaseList.addAll(savedList)
            }

            // 4. 데이터 로딩 후 화면 갱신
            binding.purchaseRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val purchaseAdapter = PurchaseAdapter(
            itemList = purchaseList,
            onItemClicked = { product ->
                // 상품 클릭 시 동작
            },
            onHeartClicked = { product ->
                if (product.isFavorite) {
                    MainActivity.wishlistStorage.addProduct(product)
                } else {
                    MainActivity.wishlistStorage.removeProduct(product)
                }
            }
        )

        binding.purchaseRecyclerView.apply {
            adapter = purchaseAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}