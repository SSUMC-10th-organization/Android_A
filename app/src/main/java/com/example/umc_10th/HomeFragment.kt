package com.example.umc_10th

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // 1. binding 변수를 클래스 최상단에 선언해야 모든 함수에서 쓸 수 있습니다.
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
        productList.apply {
            add(Product(R.drawable.shoes1, "Air Jordan XXXVI", "US$185"))
            add(Product(R.drawable.shoes2, "Nike Air Force 1 '07", "US$115"))
        }
    }

    private fun initRecyclerView() {
        // 1. 어댑터 생성
        val productAdapter = ProductAdapter(productList) { product ->
            // 클릭 시 동작 (필요할 때 작성)
        }

        // 2. 바인딩된 ID 사용 (product_recycler_view -> productRecyclerView)
        binding.productRecyclerView.adapter = productAdapter

        // 3. 레이아웃 매니저 설정
        binding.productRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}