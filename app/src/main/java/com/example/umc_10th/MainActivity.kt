package com.example.umc_10th

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvHappy: TextView
    private lateinit var tvExcited: TextView
    private lateinit var tvNormal: TextView
    private lateinit var tvAnxious: TextView
    private lateinit var tvAngry: TextView

    private var isHappySelected = false
    private var isExcitedSelected = false
    private var isNormalSelected = false
    private var isAnxiousSelected = false
    private var isAngrySelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivHappy = findViewById<ImageView>(R.id.ivHappy)
        val ivExcited = findViewById<ImageView>(R.id.ivExcited)
        val ivNormal = findViewById<ImageView>(R.id.ivNormal)
        val ivAnxious = findViewById<ImageView>(R.id.ivAnxious)
        val ivAngry = findViewById<ImageView>(R.id.ivAngry)

        tvHappy = findViewById(R.id.tvHappy)
        tvExcited = findViewById(R.id.tvExcited)
        tvNormal = findViewById(R.id.tvNormal)
        tvAnxious = findViewById(R.id.tvAnxious)
        tvAngry = findViewById(R.id.tvAngry)

        ivHappy.setOnClickListener {
            isHappySelected = !isHappySelected
            toggleColor(tvHappy, isHappySelected, R.color.purple_700)
        }

        ivExcited.setOnClickListener {
            isExcitedSelected = !isExcitedSelected
            toggleColor(tvExcited, isExcitedSelected, R.color.purple_700)
        }

        ivNormal.setOnClickListener {
            isNormalSelected = !isNormalSelected
            toggleColor(tvNormal, isNormalSelected, R.color.purple_700)
        }

        ivAnxious.setOnClickListener {
            isAnxiousSelected = !isAnxiousSelected
            toggleColor(tvAnxious, isAnxiousSelected, R.color.purple_700)
        }

        ivAngry.setOnClickListener {
            isAngrySelected = !isAngrySelected
            toggleColor(tvAngry, isAngrySelected, R.color.purple_700)
        }
    }

    private fun toggleColor(tv: TextView, isSelected: Boolean, colorRes: Int) {
        if (isSelected) {
            tv.setTextColor(getColor(colorRes))
        } else {
            tv.setTextColor(getColor(R.color.black))
        }
    }

}