package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.example.myapplication.ui.theme.MyApplicationTheme

data class Emotion(
    val iconRes: Int,
    val label: String,
    val selectedColor: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 감정 목록
        val emotions = listOf(
            Emotion(R.drawable.ic_happy_face,
                "더없이 행복한 하루였어요",
                "#FFEFB6".toColorInt()),
            Emotion(R.drawable.ic_excited_face,
                "들뜨고 흥분돼요",
                "#CEE7F5".toColorInt()),
            Emotion(R.drawable.ic_bored_face,
                "평범한 하루였어요",
                "#BEC3ED".toColorInt()),
            Emotion(R.drawable.ic_bad_face,
                "생각이 많아지고 불안해요",
                "#B1D3B9".toColorInt()),
            Emotion(R.drawable.ic_angry_face,
                "부글부글 화가 나요",
                "#EB8B8B".toColorInt())
        )

        val container = findViewById<LinearLayout>(R.id.emotionContainer)

        emotions.forEach { emotion ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_emotion, container, false)

            val iconView = itemView.findViewById<ImageView>(R.id.emotionIcon)
            val labelView = itemView.findViewById<TextView>(R.id.emotionLabel)

            iconView.setImageResource(emotion.iconRes)
            labelView.text = emotion.label

            var isSelected = false

            iconView.setOnClickListener {
                isSelected = !isSelected
                if (isSelected) {
                    labelView.setTextColor(emotion.selectedColor)
                } else {
                    labelView.setTextColor(android.graphics.Color.BLACK)
                }
            }

            container.addView(itemView)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}