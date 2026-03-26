package com.example.umc_10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.databinding.ItemHomeBinding // 본인이 만든 아이템 레이아웃 바인딩

class ProductAdapter(
    private val productList: MutableList<Product>,
    private val onItemClicked: (Product) -> Unit // 클릭 이벤트가 필요 없다면 생략 가능
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // 1. ViewHolder: bind 함수를 내부에 두는 UMC 스타일
    inner class ProductViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productImage.setImageResource(product.imageRes)
            binding.productName.text = product.name ?: "이름 없음"
            binding.productPrice.text = product.price ?: "가격 미정"

            // 아이템 전체 클릭 시 이벤트 처리 (필요한 경우)
            binding.root.setOnClickListener {
                onItemClicked(product)
            }
        }
    }

    // 2. onCreateViewHolder: 바인딩 객체를 생성해서 넘겨줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductViewHolder(binding)
    }

    // 3. onBindViewHolder: bind() 호출만 수행
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}