package com.example.umc_10th.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.databinding.FragmentWishlistBinding
import com.example.umc_10th.ui.product.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    // 1. ViewBinding 설정
    private val viewModel: WishlistViewModel by viewModels()
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. 바인딩 객체 인플레이트
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3. 리사이클러뷰 초기화 함수 호출
        initRecyclerView()
        observeViewModel()
        viewModel.fetchWishlist() // 데이터 요청
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.wishlist.collect { list ->
                // 어댑터에 updateList 함수가 있다면 사용 (없으면 AllFragment 때처럼 추가!)
                wishlistAdapter.updateList(list)
            }
        }
    }


    private fun initRecyclerView() {
        wishlistAdapter = WishlistAdapter(
            itemList = mutableListOf(),
            onHeartClicked = { product ->
                // 직접 리스트 지우지 않고 뷰모델에게 토스!
                viewModel.removeFromWishlist(product)
            }
        )
        binding.wishlistRecyclerView.apply {
            adapter = wishlistAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    // 7. 메모리 누수 방지를 위한 바인딩 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}