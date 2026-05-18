package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.umc_10th.databinding.ProfileFragmentBinding
import com.example.umc_10th.network.RetrofitClient
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 1번 유저 정보 가져오기
                val user = RetrofitClient.api.getUser(1).data
                binding.tvNickname.text = "${user.first_name} ${user.last_name}"
                Glide.with(this@ProfileFragment)
                    .load(user.avatar)
                    .apply(RequestOptions().circleCrop())
                    .into(binding.ivProfile)

                // 팔로잉 리스트 가져오기
                val users = RetrofitClient.api.getUserList(1).data
                binding.tvFollowingCount.text = "팔로잉 (${users.size})"
                binding.rcFollowing.adapter = FollowingAdapter(users)
                binding.rcFollowing.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
            } catch (e: Exception) {
                // 네트워크 오류 시 기본값 유지
            }
        }
    }
}
