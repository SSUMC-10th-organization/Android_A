package com.example.umc_10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_10th.databinding.ItemFollowingBinding

class FollowingAdapter(
    private val followingList: MutableList<FollowingItem>
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(
        private val binding: ItemFollowingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FollowingItem) {
            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.ivFollowing)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemFollowingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followingList[position])
    }

    override fun getItemCount(): Int = followingList.size

    fun submitList(newList: List<FollowingItem>) {
        followingList.clear()
        followingList.addAll(newList)
        notifyDataSetChanged()
    }
}