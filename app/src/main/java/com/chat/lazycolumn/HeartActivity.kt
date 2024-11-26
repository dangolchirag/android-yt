package com.chat.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chat.lazycolumn.ui.theme.LazyColumnTheme

class HeartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                HeartScreen()
            }
        }
    }
}

@Composable
fun HeartScreen() {
    var usedWaterAmount by remember { mutableIntStateOf(200) }

    val fillPercentage by animateFloatAsState(
        targetValue = usedWaterAmount / 1000f,
        label = "Heart Fill Animation",
        animationSpec = tween(durationMillis = 4000)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEAEA))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val radius = size.minDimension
            val adjust = height / 2f - radius / 2f

            val heartPath = Path().apply {
                moveTo(width / 2f, radius / 5f + adjust)

                cubicTo(
                    x1 = width / 8f,
                    y1 = -radius / 5f + adjust,
                    x2 = -width / 5f,
                    y2 = radius / 2.5f + adjust,
                    x3 = width / 2f,
                    y3 = radius + adjust
                )

                cubicTo(
                    x1 = width + width / 5f,
                    y1 = radius / 2.5f + adjust,
                    x2 = width - width / 8f,
                    y2 = -radius / 5f + adjust,
                    x3 = width / 2f,
                    y3 = radius / 5f + adjust
                )
            }


            clipPath(heartPath) {

                val waterWavesYPosition = (1 - fillPercentage) * height

                val wavePath = Path().apply {
                    moveTo(0f, waterWavesYPosition)
                    for (i in 0 until 6) {
                        val waveWidth = width / 5f
                        cubicTo(
                            x1 = waveWidth * i + waveWidth / 2,
                            y1 = waterWavesYPosition - 20f,
                            x2 = waveWidth * i + waveWidth / 2,
                            y2 = waterWavesYPosition + 20f,
                            x3 = waveWidth * (i + 1),
                            y3 = waterWavesYPosition
                        )
                    }
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                drawRect(color = Color.White, size = size)

                drawPath(
                    path = wavePath, color = Color(0xFFF44336)
                )
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(onClick = {
            usedWaterAmount = 800
        }) {
            Text("Fill")
        }
        Spacer(Modifier.width(24.dp))
        OutlinedButton(onClick = {
            usedWaterAmount = 200
        }) {
            Text("Empty")
        }
    }
}

@Preview
@Composable
private fun HeartScreenPreview() {
    LazyColumnTheme {
        HeartScreen()
    }
}

