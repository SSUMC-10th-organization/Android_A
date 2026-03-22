package com.example.umc_10th

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_10th.databinding.ActivityMainBinding
import android.graphics.Color
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ivYellow.setOnClickListener {
            binding.tvYellow.setTextColor(Color.parseColor("#FFEFB6"))
        }
        binding.ivSkyblue.setOnClickListener {
            binding.tvSkyblue.setTextColor(Color.parseColor("#CEE7F5"))
        }
        binding.ivPurple.setOnClickListener {
            binding.tvPurple.setTextColor(Color.parseColor("#BEC3ED"))
        }
        binding.ivGreen.setOnClickListener {
            binding.tvGreen.setTextColor(Color.parseColor("#B1D3B9"))
        }
        binding.ivRed.setOnClickListener {
            binding.tvRed.setTextColor(Color.parseColor("#EB8B8B"))
        }
    }

}