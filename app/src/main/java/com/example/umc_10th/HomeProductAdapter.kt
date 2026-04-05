package com.example.umc_10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.databinding.ItemHomeProductBinding

class HomeProductAdapter(
    private val productList: MutableList<ProductData>
) : RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>() {

    inner class HomeProductViewHolder(
        private val binding: ItemHomeProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductData) {
            binding.ivHomeProductImage.setImageResource(product.imageResId)
            binding.tvHomeProductName.text = product.name
            binding.tvHomeProductPrice.text = product.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder {
        val binding = ItemHomeProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun submitList(newList: List<ProductData>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }
}