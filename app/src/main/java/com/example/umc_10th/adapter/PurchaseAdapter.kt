package com.example.umc_10th.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.data.PurchaseProduct
import com.example.umc_10th.R
import com.example.umc_10th.databinding.ItemPurchaseBinding

class PurchaseAdapter(
    private val itemList: MutableList<PurchaseProduct>,
    private val onItemClicked: (PurchaseProduct) -> Unit,
    // 1. [추가] 하트 클릭 시 프래그먼트에 알려줄 콜백 함수
    private val onHeartClicked: (PurchaseProduct) -> Unit
) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    inner class PurchaseViewHolder(val binding: ItemPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: PurchaseProduct) {
            binding.productImage.setImageResource(product.imageRes)
            binding.productName.text = product.name
            binding.productExplain.text = product.explain
            binding.productPrice.text = product.price

            // 2. [체크] 현재 데이터의 찜 상태에 따라 이미지 설정
            if (product.isFavorite) {
                binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_heart) // 빨간 하트
            } else {
                binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_border) // 빈 하트
            }

            // 3. [추가] 하트 버튼 클릭 리스너
            binding.productHeartBtn.setOnClickListener {
                // 데이터 상태 반전 (true -> false, false -> true)
                product.isFavorite = !product.isFavorite

                // 클릭된 아이템 하나만 다시 그리기 (이미지 변경)
                notifyItemChanged(adapterPosition)

                // 프래그먼트로 '이 상품 찜 상태 변함' 알림 전달
                onHeartClicked(product)
            }

            binding.root.setOnClickListener {
                onItemClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val binding = ItemPurchaseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}