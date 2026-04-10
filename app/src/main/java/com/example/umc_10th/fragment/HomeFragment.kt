package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.MainActivity
import com.example.umc_10th.data.Product
import com.example.umc_10th.adapter.ProductAdapter
import com.example.umc_10th.R
import com.example.umc_10th.data.SharedPreferenceManager
import com.example.umc_10th.databinding.FragmentHomeBinding
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDummyData()
        initRecyclerView()
    }

    private fun initDummyData() {
        val type = object : TypeToken<List<Product>>() {}.type

        viewLifecycleOwner.lifecycleScope.launch {
            // 1. DataStore에서 먼저 데이터를 읽어옴
            val savedList = MainActivity.prefManager.getObjectList<Product>(
                SharedPreferenceManager.KEY_HOME_PRODUCTS, type
            ).first()

            if (savedList.isEmpty()) {
                // 2. 데이터가 없으면 더미 데이터 생성 및 저장
                val dummy = mutableListOf(
                    Product(R.drawable.shoes1, "Air Jordan XXXVI", "US$185"),
                    Product(R.drawable.shoes2, "Nike Air Force 1 '07", "US$115")
                )
                productList.clear()
                productList.addAll(dummy)

                MainActivity.prefManager.saveObjectList(
                    SharedPreferenceManager.KEY_HOME_PRODUCTS,
                    dummy
                )
            } else {
                // 3. 저장된 데이터가 있으면 리스트에 채우기
                productList.clear()
                productList.addAll(savedList)
            }

            // 4. 비동기 로딩이 끝났으므로 어댑터에 데이터 변경 알림
            binding.productRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val productAdapter = ProductAdapter(productList) { product ->
            // 클릭 시 동작
        }

        binding.productRecyclerView.adapter = productAdapter
        binding.productRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}