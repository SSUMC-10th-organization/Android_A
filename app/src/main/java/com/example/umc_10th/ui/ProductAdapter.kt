package com.example.umc_10th.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.databinding.ItemProductBinding

class ProductAdapter(
    private var productList: MutableList<ProductData>,
    private val onClicked: (ProductData) -> Unit,
    private val onHeartClicked: ((Int, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateList(newList: List<ProductData>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductData, position: Int) {
            binding.ivProduct.setImageResource(product.img)
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productName.setOnClickListener { onClicked(product) }
            binding.productPrice.setOnClickListener { onClicked(product) }
            binding.ivProduct.setOnClickListener { onClicked(product) }

            // 재활용 시 이전 상태가 남지 않도록 리스너를 먼저 제거
            binding.btnHeart.setOnCheckedChangeListener(null)
            binding.btnHeart.isChecked = product.isLiked
            binding.btnHeart.setOnCheckedChangeListener { _, isChecked ->
                product.isLiked = isChecked
                onHeartClicked?.invoke(position, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], position)
    }

    override fun getItemCount(): Int = productList.size
}
