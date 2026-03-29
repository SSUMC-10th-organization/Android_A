package com.example.umc_10th

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_10th.databinding.ActivityProductDetailBinding

class ProductDetailActivity: AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val product = intent.getSerializableExtra("detail_info") as? DetailProductData;

        product?. let {
            binding.tvProductName.text = it.name
            binding.ivProduct.setImageResource(it.imageRes)
            binding.tvCategory.text = it.category
            binding.tvMainTitle.text = it.name
            binding.tvPrice.text = it.price
            binding.tvDescription.text = it.description
            binding.tvColorInfo.text = " - Shown: ${it.color}"
            binding.tvStyleCode.text = " - Style: ${it.styleCode}"
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}