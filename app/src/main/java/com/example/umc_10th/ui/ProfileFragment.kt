package com.example.umc_10th.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.umc_10th.databinding.ProfileFragmentBinding
import com.example.umc_10th.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileViewModel by viewModels()

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
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvNickname.text = state.nickname
                    if (state.avatarUrl.isNotEmpty()) {
                        Glide.with(this@ProfileFragment)
                            .load(state.avatarUrl)
                            .apply(RequestOptions().circleCrop())
                            .into(binding.ivProfile)
                    }
                    if (state.followingList.isNotEmpty()) {
                        binding.tvFollowingCount.text = "팔로잉 (${state.followingList.size})"
                        binding.rcFollowing.adapter = FollowingAdapter(state.followingList)
                        binding.rcFollowing.layoutManager = LinearLayoutManager(
                            requireContext(), LinearLayoutManager.HORIZONTAL, false
                        )
                    }
                }
            }
        }
    }
}
