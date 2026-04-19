package com.example.umc_10th.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.R
import com.example.umc_10th.data.model.PurchaseProduct
import com.example.umc_10th.databinding.ItemWishlistBinding

//1. 생성자와 매개변수
class WishlistAdapter(
    private val itemList: MutableList<PurchaseProduct>,
    //위시리스트에 보여줄 데이터(itemList), PurchaseProduct 데이터 클래스를 공유해서 사용
    private val onHeartClicked: (PurchaseProduct) -> Unit
    //하트를 눌러 찜을 해제할 때 사용할 콜백(onHeartClicked)
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(val binding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

            //2. ViewHolder (Bind)
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

    //3. 상태 고정과 클릭 리스너 (onBindViewHolder)
    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = itemList[position] // 현재 아이템 가져오기
        holder.bind(item)

        // if-else 분기 없이 항상 **빨간 하트(ic_favorite_heart)**로 보이도록 설정
        holder.binding.productHeartBtn.setImageResource(R.drawable.ic_favorite_heart)

        // 하트 버튼 클릭 시 리스너 호출 (핵심!)
        holder.binding.productHeartBtn.setOnClickListener {
            onHeartClicked(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun updateList(newList: List<PurchaseProduct>) {
        // 1. 기존 리스트(itemList)의 모든 데이터를 비웁니다.
        itemList.clear()

        // 2. 전달받은 최신 리스트의 모든 데이터를 추가합니다.
        itemList.addAll(newList)

        // 3. 데이터가 변경되었음을 어댑터에 알려 리사이클러뷰를 다시 그리게 합니다.
        notifyDataSetChanged()
    }
}