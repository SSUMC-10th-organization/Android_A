package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_10th.MainActivity
import com.example.umc_10th.adapter.FollowingAdapter
import com.example.umc_10th.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileViewModel = (requireActivity() as MainActivity).profileViewModel

        profileViewModel.userProfile.observe(viewLifecycleOwner) { user ->
            user ?: return@observe
            binding.tvNickname.text = "${user.firstName} ${user.lastName}"
            Glide.with(this)
                .load(user.avatar)
                .circleCrop()
                .into(binding.ivProfile)
        }

        profileViewModel.followingList.observe(viewLifecycleOwner) { following ->
            binding.tvFollowingTitle.text = "팔로잉 (${following.size})"
            binding.rvFollowing.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvFollowing.adapter = FollowingAdapter(following)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}