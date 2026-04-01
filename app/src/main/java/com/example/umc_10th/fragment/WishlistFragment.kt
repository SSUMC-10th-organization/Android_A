package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_10th.MainActivity
import com.example.umc_10th.adapter.WishlistAdapter
import com.example.umc_10th.data.WishlistStorage
import com.example.umc_10th.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    // 1. ViewBinding 설정
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun initRecyclerView() {
        // 4. WishlistStorage에서 찜한 데이터 리스트 가져오기
        val wishlistData = MainActivity.wishlistStorage.getWishlist()

        // 5. 하트가 없는 전용 어댑터(WishlistAdapter) 생성
        val wishlistAdapter = WishlistAdapter(wishlistData.toMutableList())

        // 6. 리사이클러뷰 설정 (2열 격자)
        binding.wishlistRecyclerView.apply {
            adapter = wishlistAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    // 7. 메모리 누수 방지를 위한 바인딩 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}