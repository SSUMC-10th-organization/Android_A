package com.example.umc_10th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_10th.databinding.FragmentProfileBinding
import com.example.umc_10th.retrofit.ApiClient
import com.example.umc_10th.retrofit.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy {
        UserRepository(ApiClient.userService)
    }

    private lateinit var followingAdapter: FollowingAdapter

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

        initFollowingRecyclerView()
        setCurrentJoinDate()
        initClickListeners()
        loadUserProfile()
        loadUserList()
    }

    private fun loadUserProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.getUser(1)

            result.onSuccess { user ->
                binding.tvNickname.text = "${user.firstName} ${user.lastName}"

                Glide.with(requireContext())
                    .load(user.avatar)
                    .into(binding.ivProfile)
            }.onFailure { e ->
                Log.e("ProfileFragment", "유저 정보 불러오기 실패", e)
                Toast.makeText(
                    requireContext(),
                    "유저 정보 불러오기 실패: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initFollowingRecyclerView() {
        followingAdapter = FollowingAdapter(mutableListOf())

        binding.rvFollowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvFollowing.adapter = followingAdapter
    }

    private fun loadUserList() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.getUserList(1)

            result.onSuccess { userList ->
                val followingList = userList.take(3).map { user ->
                    FollowingItem(imageUrl = user.avatar)
                }

                followingAdapter.submitList(followingList)
                binding.tvFollowingTitle.text = "팔로잉 (${followingList.size})"
            }.onFailure { e ->
                Log.e("ProfileFragment", "유저 리스트 불러오기 실패", e)
                Toast.makeText(
                    requireContext(),
                    "유저 리스트 불러오기 실패: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setCurrentJoinDate() {
        val currentDate = SimpleDateFormat("yyyy년 M월", Locale.KOREA).format(Date())
        binding.tvJoinDate.text = "회원 가입일 : $currentDate"
    }

    private fun initClickListeners() {
        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "프로필 수정 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnOrder.setOnClickListener {
            Toast.makeText(requireContext(), "주문 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnPass.setOnClickListener {
            Toast.makeText(requireContext(), "패스 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnEvent.setOnClickListener {
            Toast.makeText(requireContext(), "이벤트 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnSetting.setOnClickListener {
            Toast.makeText(requireContext(), "설정 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnMemberBenefit.setOnClickListener {
            Toast.makeText(requireContext(), "나이키 멤버 혜택 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnArrow.setOnClickListener {
            Toast.makeText(requireContext(), "화살표 클릭", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditFollowing.setOnClickListener {
            Toast.makeText(requireContext(), "편집 클릭", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}