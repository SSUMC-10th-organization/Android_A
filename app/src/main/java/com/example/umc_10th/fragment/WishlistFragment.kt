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
        // 1. WishlistStorage에서 데이터 가져오기
        val wishlistData = MainActivity.wishlistStorage.getWishlist().toMutableList()

        // 2. 어댑터 생성 시 클릭 리스너(삭제 로직) 전달
        val wishlistAdapter = WishlistAdapter(
            itemList = wishlistData,
            onHeartClicked = { product ->
                // 🛑 [핵심] 저장소에서 삭제
                MainActivity.wishlistStorage.removeProduct(product)

                // 🔄 [핵심] 현재 위시리스트 화면에서 즉시 제거 및 갱신
                wishlistData.remove(product)
                binding.wishlistRecyclerView.adapter?.notifyDataSetChanged()

                // 이제 사용자가 '구매하기' 탭으로 돌아가면
                // 그쪽의 onResume이 돌면서 하트가 빈 상태로 바뀔 거예요!
            }
        )

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