package com.example.umc_10th.ui.purchase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.R
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.databinding.ItemPurchaseBinding

//1. 확장된 생성자와 콜백 함수
class PurchaseAdapter(
    private val itemList: MutableList<PurchaseProduct>,
    //찜 상태(isFavorite)를 내부에서 변경해야 하므로, 데이터 수정이 가능한 리스트 타입(MutableList) 선언
    private val onItemClicked: (PurchaseProduct) -> Unit,
    // 아이템 전체 클릭 이벤트
    private val onHeartClicked: (PurchaseProduct) -> Unit
    // 하트 클릭 이벤트
) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    inner class PurchaseViewHolder(val binding: ItemPurchaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: PurchaseProduct) {
            binding.productImage.setImageResource(product.imageRes)
            binding.productName.text = product.name
            binding.productExplain.text = product.explain
            binding.productPrice.text = product.price

            // 2. 상태에 따른 UI 분기 (Conditional UI)
            if (product.isFavorite) {
                binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_heart) // 빨간 하트
            } else {
                binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_border) // 빈 하트
            }

            // 3. 데이터 반전과 효율적인 갱신 (Toggle & Partial Update)
            binding.productHeartBtn.setOnClickListener {

                // 논리 부정 연산자(!)를 사용해 상태를 간단히 토글
                product.isFavorite = !product.isFavorite

                // 클릭된 아이템 하나만 다시 그리기 (이미지 변경)
                notifyItemChanged(bindingAdapterPosition)

                // fragment로 '이 상품 하트 버튼 상태 변함' 알림 전달
                onHeartClicked(product)
            }

            binding.root.setOnClickListener {
                onItemClicked(product)
                /*변경 사항을 데이터베이스(DB)나 서버에 저장해야 할 경우가 있기에
                이 callback을 전달해준 Fragment나 Activity에서 처리*/
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