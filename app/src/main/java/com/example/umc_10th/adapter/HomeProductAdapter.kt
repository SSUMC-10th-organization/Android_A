package com.example.umc_10th.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_10th.Product
import com.example.umc_10th.R

class HomeProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder>() {

    inner class HomeProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.iv_product)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_home, parent, false)
        return HomeProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        val product = products[position]
        holder.ivProduct.setImageResource(product.imageRes)
        holder.tvName.text = product.name
        holder.tvPrice.text = product.price

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}