package com.example.umc_10th.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.umc_10th.databinding.FragmentProfileBinding
import com.example.umc_10th.api.ApiClient
import kotlinx.coroutines.launch

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
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.userService.getUser(1)
                if (response.isSuccessful) {
                    val user = response.body()?.data ?: return@launch
                    binding.tvNickname.text = "${user.firstName} ${user.lastName}"
                    Glide.with(this@ProfileFragment)
                        .load(user.avatar)
                        .circleCrop()
                        .into(binding.ivProfile)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}