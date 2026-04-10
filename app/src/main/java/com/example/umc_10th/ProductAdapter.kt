package com.example.umc_10th

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.databinding.ItemProductBinding

class ProductAdapter(
    private val productList: MutableList<ProductData>,
    private val onLikeClicked: (ProductData) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductData) {
            binding.ivProductImage.setImageResource(product.imageResId)
            binding.tvProductName.text = product.name
            binding.tvCategory.text = product.category
            binding.tvColorCount.text = "${product.colorCount} Colours"
            binding.tvPrice.text = product.price

            binding.tvBestSeller.visibility =
                if (product.isBestSeller) VISIBLE else GONE

            binding.btnLike.setImageResource(
                if (product.isLiked) R.drawable.ic_heart_filled
                else R.drawable.ic_heart_empty
            )

            binding.btnLike.setOnClickListener {
                onLikeClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun submitList(newList: List<ProductData>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}