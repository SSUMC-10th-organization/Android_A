package com.example.umc_10th.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_10th.data.UserData
import com.example.umc_10th.databinding.ItemFollowingBinding

class ProfileAdapter(private var userList: List<UserData>) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    // 뷰홀더: item_following.xml의 위젯들을 잡고 있는 역할
    inner class ProfileViewHolder(private val binding: ItemFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserData) {
            // Glide를 사용하여 URL 이미지를ImageView에 로드
            Glide.with(binding.ivFollowingUser.context)
                .load(user.avatar)
                .placeholder(android.R.color.darker_gray) // 로딩 중 보일 색상
                .into(binding.ivFollowingUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemFollowingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    // 데이터 업데이트용 함수 (Retrofit 결과 받으면 사용)
    fun updateData(newList: List<UserData>) {
        userList = newList
        notifyDataSetChanged()
    }
}