package com.example.umc_10th.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.R
import com.example.umc_10th.data.PurchaseProduct
import com.example.umc_10th.databinding.ItemWishlistBinding

class WishlistAdapter(
    private val itemList: MutableList<PurchaseProduct>,
    private val onHeartClicked: (PurchaseProduct) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: PurchaseProduct) {
            binding.productImage.setImageResource(product.imageRes)
            binding.productName.text = product.name
            binding.productExplain.text = product.explain
            binding.productPrice.text = product.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ItemWishlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WishlistViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = itemList[position] // 현재 아이템 가져오기
        holder.bind(item)

        // 1. 하트 아이콘 설정 (이미 빨간색 하트로 고정)
        holder.binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_heart)

        // 2. 하트 버튼 클릭 시 리스너 호출 (핵심!)
        holder.binding.productHeartBtn.setOnClickListener {
            onHeartClicked(item)
        }
    }

    override fun getItemCount(): Int = itemList.size
}