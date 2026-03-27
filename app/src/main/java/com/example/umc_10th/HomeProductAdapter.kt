package com.example.umc_10th

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.databinding.ItemProductBinding

class HomeProductAdapter(
    private var productList: MutableList<ProductData>,
    private val onClicked: (ProductData) -> Unit): RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(var binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductData) {
            binding.ivProduct.setImageResource(product.img);
            binding.productName.text = product.name;
            binding.productPrice.text = product.price;
            binding.productName.setOnClickListener { onClicked(product) }
            binding.productPrice.setOnClickListener { onClicked(product) }
            binding.ivProduct.setOnClickListener { onClicked(product) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding);
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val friendData = productList[position]
        holder.bind(friendData)
    }

    override fun getItemCount(): Int {
        return productList.size;
    }

}