package com.example.umc_10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// 1. 홈 화면용 레이아웃 바인딩으로 복구
import com.example.umc_10th.databinding.ItemHomeBinding

class ProductAdapter(
    private val productList: MutableList<Product>,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // 2. 홈용 데이터 클래스(인자 3개)에 맞춰 바인딩
            binding.productImage.setImageResource(product.imageRes)
            binding.productName.text = product.name


            binding.productPrice.text = product.price

            binding.root.setOnClickListener {
                onItemClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // 3. ItemHomeBinding을 인플레이트하도록 복구
        val binding = ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}