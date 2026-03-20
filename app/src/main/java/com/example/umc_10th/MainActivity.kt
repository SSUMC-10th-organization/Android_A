package com.example.umc_10th

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_10th.databinding.ActivityMainBinding

import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ivHappy.setOnClickListener {
            binding.tvHappy.setTextColor(ContextCompat.getColor(this, R.color.happy));
            binding.tvHappy.setBackgroundColor(ContextCompat.getColor(this, R.color.background_happy));
        }

        binding.ivExcited.setOnClickListener {
            binding.tvExcited.setTextColor(ContextCompat.getColor(this, R.color.excited));
            binding.tvExcited.setBackgroundColor(ContextCompat.getColor(this, R.color.background_excited));
        }
        binding.ivNormal.setOnClickListener {
            binding.tvNormal.setTextColor(ContextCompat.getColor(this, R.color.normal));
            binding.tvNormal.setBackgroundColor(ContextCompat.getColor(this, R.color.background_normal));
        }
        binding.ivSad.setOnClickListener {
            binding.tvSad.setTextColor(ContextCompat.getColor(this, R.color.sad));
            binding.tvSad.setBackgroundColor(ContextCompat.getColor(this, R.color.background_sad));
        }
        binding.ivAngry.setOnClickListener {
            binding.tvAngry.setTextColor(ContextCompat.getColor(this, R.color.angry));
            binding.tvAngry.setBackgroundColor(ContextCompat.getColor(this, R.color.background_angry));
        }
    }
}