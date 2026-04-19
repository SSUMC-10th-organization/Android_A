package com.example.umc_10th.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_10th.R
import com.example.umc_10th.data.local.SharedPreferenceManager
import com.example.umc_10th.data.model.Product
import com.example.umc_10th.databinding.FragmentHomeBinding
import com.example.umc_10th.ui.main.MainActivity
import com.example.umc_10th.ui.product.ProductAdapter
import com.example.umc_10th.ui.viewmodel.HomeViewModel
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // [1] ViewModel 주입
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel() // [2] 뷰모델 관찰 시작

        viewModel.fetchHomeProducts() // [3] 데이터 요청
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            // 뷰모델의 products 상태를 관찰하다가 데이터가 오면 어댑터 갱신
            viewModel.products.collect { list ->
                // ProductAdapter에 updateData 같은 함수가 없다면 리스트 통째로 교체
                // productAdapter = ProductAdapter(list) { ... } 식의 기존 로직 활용
                (binding.productRecyclerView.adapter as? ProductAdapter)?.let {
                    // 어댑터 내부에 리스트를 업데이트하는 함수가 있다고 가정 (없으면 새로 만들기!)
                    it.updateList(list)
                }
            }
        }
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(mutableListOf()) { product ->
            // 클릭 시 동작
        }
        binding.productRecyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}