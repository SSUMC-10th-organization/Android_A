package com.example.umc_10th

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // Intent에서 데이터 받기
        val productName = intent.getStringExtra("product_name") ?: ""
        val productDescription = intent.getStringExtra("product_description") ?: ""
        val productPrice = intent.getStringExtra("product_price") ?: ""
        val productImageRes = intent.getIntExtra("product_image", 0)
        var isFavorite = intent.getBooleanExtra("product_favorite", false)

        // 뷰 연결
        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val tvToolbarTitle = findViewById<TextView>(R.id.tv_toolbar_title)
        val ivProduct = findViewById<ImageView>(R.id.iv_detail_product)
        val tvName = findViewById<TextView>(R.id.tv_detail_name)
        val tvDescription = findViewById<TextView>(R.id.tv_detail_description)
        val tvPrice = findViewById<TextView>(R.id.tv_detail_price)
        val btnAddToCart = findViewById<Button>(R.id.btn_add_to_cart)
        val btnWishlist = findViewById<LinearLayout>(R.id.btn_wishlist)
        val ivFavorite = findViewById<ImageView>(R.id.iv_detail_favorite)

        // 데이터 세팅
        tvToolbarTitle.text = productName
        ivProduct.setImageResource(productImageRes)
        tvName.text = productName
        tvDescription.text = productDescription
        tvPrice.text = productPrice
        updateFavoriteIcon(ivFavorite, isFavorite)

        // 뒤로가기
        ivBack.setOnClickListener { finish() }

        // 장바구니 추가
        btnAddToCart.setOnClickListener {
            Toast.makeText(this, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show()
        }

        // 위시리스트 토글
        btnWishlist.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon(ivFavorite, isFavorite)
            val msg = if (isFavorite) "위시리스트에 추가됨" else "위시리스트에서 제거됨"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteIcon(iv: ImageView, isFavorite: Boolean) {
        iv.setImageResource(
            if (isFavorite) R.drawable.ic_heart_filled
            else R.drawable.ic_heart_empty
        )
    }
}