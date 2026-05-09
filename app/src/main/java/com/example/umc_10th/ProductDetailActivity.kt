package com.example.umc_10th

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_10th.databinding.ActivityProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent에서 데이터 받기
        val productId = intent.getIntExtra("product_id", -1)
        val productName = intent.getStringExtra("product_name") ?: ""
        val productDescription = intent.getStringExtra("product_description") ?: ""
        val productPrice = intent.getStringExtra("product_price") ?: ""
        val productImageRes = intent.getIntExtra("product_image", 0)
        var isFavorite = intent.getBooleanExtra("product_favorite", false)

        // 데이터 세팅
        binding.tvToolbarTitle.text = productName
        binding.ivDetailProduct.setImageResource(productImageRes)
        binding.tvDetailName.text = productName
        binding.tvDetailDescription.text = productDescription
        binding.tvDetailPrice.text = productPrice
        updateFavoriteIcon(isFavorite)

        // 뒤로가기
        binding.ivBack.setOnClickListener { finish() }

        // 장바구니 추가
        binding.btnAddToCart.setOnClickListener {
        }

        // 위시리스트 토글
        binding.btnWishlist.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteIcon(isFavorite)
            if (productId != -1) {
                viewModel.toggleFavorite(productId, isFavorite)
            }
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.btnWishlist.setIconResource(
            if (isFavorite) R.drawable.ic_heart_filled
            else R.drawable.ic_heart_empty
        )
    }
}
