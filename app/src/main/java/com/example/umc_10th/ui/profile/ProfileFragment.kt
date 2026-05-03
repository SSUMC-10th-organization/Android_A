package com.example.umc_10th.ui.profile

import androidx.fragment.app.Fragment
import com.example.umc_10th.R

import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile){
    private val viewModel: ProfileViewModel by viewModels()

}