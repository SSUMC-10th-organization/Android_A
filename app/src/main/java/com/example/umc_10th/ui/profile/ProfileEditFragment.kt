package com.example.umc_10th.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.umc_10th.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels

@AndroidEntryPoint
class ProfileEditFragment : Fragment(R.layout.fragment_profile_edit) {

    private val viewModel: ProfileViewModel by viewModels()
}