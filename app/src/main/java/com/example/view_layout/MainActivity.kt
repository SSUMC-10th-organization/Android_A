package com.example.view_layout

import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainscreen)

        val smileFace = findViewById<ImageView>(R.id.iv_smile_face)
        val happyFace = findViewById<ImageView>(R.id.iv_happy_face)
        val notbadFace = findViewById<ImageView>(R.id.iv_notbad_face)
        val badFace = findViewById<ImageView>(R.id.iv_bad_face)
        val angryFace = findViewById<ImageView>(R.id.iv_angry_face)

        val smileText = findViewById<TextView>(R.id.iv_smile_text)
        val happyText = findViewById<TextView>(R.id.iv_happy_text)
        val notbadText = findViewById<TextView>(R.id.iv_notbad_text)
        val badText = findViewById<TextView>(R.id.iv_bad_text)
        val angryText = findViewById<TextView>(R.id.iv_angry_text)


        smileFace.setOnClickListener {
            smileText.setTextColor(Color.YELLOW)
        }

        happyFace.setOnClickListener {
            happyText.setTextColor(Color.BLUE)
        }

        notbadFace.setOnClickListener {
            notbadText.setTextColor(Color.MAGENTA)
        }

        badFace.setOnClickListener {
            badText.setTextColor(Color.GREEN)
        }

        angryFace.setOnClickListener {
            angryText.setTextColor(Color.RED)
        }
    }
}