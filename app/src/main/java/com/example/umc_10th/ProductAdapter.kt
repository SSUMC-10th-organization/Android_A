package com.example.umc_10th

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val products: MutableList<Product>,
    private val onItemClick: (Product) -> Unit,
    private val onFavoriteClick: (Product, Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.iv_product)
        val ivFavorite: ImageView = itemView.findViewById(R.id.iv_favorite)
        val tvBestSeller: TextView = itemView.findViewById(R.id.tv_best_seller)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvColorInfo: TextView = itemView.findViewById(R.id.tv_color_info)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_grid, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.ivProduct.setImageResource(product.imageRes)
        holder.tvName.text = product.name
        holder.tvDescription.text = product.description
        holder.tvPrice.text = product.price

        // 색상 정보
        if (product.colorInfo.isNotEmpty()) {
            holder.tvColorInfo.text = product.colorInfo
            holder.tvColorInfo.visibility = View.VISIBLE
        } else {
            holder.tvColorInfo.visibility = View.GONE
        }

        // BestSeller 뱃지
        holder.tvBestSeller.visibility = if (product.isBestSeller) View.VISIBLE else View.GONE

        // 하트 아이콘
        holder.ivFavorite.setImageResource(
            if (product.isFavorite) R.drawable.ic_heart_filled
            else R.drawable.ic_heart_empty
        )

        // 하트 클릭
        holder.ivFavorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            notifyItemChanged(position)
            onFavoriteClick(product, position)
        }

        // 아이템 클릭 → 상세 페이지
        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}