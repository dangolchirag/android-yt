package com.chat.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chat.lazycolumn.ui.theme.LazyColumnTheme

class InfinityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                Scaffold { padding ->
                    InfinityScreen(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun InfinityScreen(modifier: Modifier = Modifier) {

    val animatedProgress = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 4000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0XFFEAEAEA))
    ) {
        val width = size.width
        val height = size.height
        
        val halfWidth = width / 2
        val halfHeight = height / 2

        val path = Path().apply {
            moveTo( halfWidth, halfHeight)

            cubicTo(
                x1 = 0f,
                y1 = halfHeight / 2,
                x2 = 0f,
                y2 = halfHeight * 3 / 2,
                x3 = halfWidth,
                y3 = halfHeight
            )

            cubicTo(
                x1 = width,
                y1 = halfHeight / 2,
                x2 = width,
                y2 = halfHeight * 3 / 2,
                x3 = halfWidth,
                y3 = halfHeight
            )
        }

        val shineWidth = size.width
        val shineOffSet = (size.width + shineWidth) * animatedProgress.value - shineWidth

        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path,false)

        val shineBrushWhite = Brush.linearGradient(
            colors = listOf(
                Color.Transparent,
                Color.White,
                Color.Transparent
            ),
            start = Offset(shineOffSet, 0f),
            end = Offset(shineOffSet + shineWidth, size.height / 2)
        )

        val multiplier = (pathMeasure.length * animatedProgress.value)

        val animatedPath = Path().apply {
            pathMeasure.getSegment(
                multiplier - 40.dp.toPx(),
                multiplier - 30.dp.toPx(),
                this,
                true
            )
        }

        drawPath(
            path = path,
            color = Color(0xFF265154),
            style = Stroke(width = 8.dp.toPx())
        )

        drawPath(
            path = path,
            brush = shineBrushWhite,
            style = Stroke(width = 8.dp.toPx())
        )
        drawPath(
            path = animatedPath,
            color = Color(0xFFC0C0C0),
            style = Stroke(width = 12.dp.toPx())
        )
    }

}

@Preview
@Composable
private fun InfinityScreenPreview() {
    LazyColumnTheme {
        Scaffold { padding ->
            InfinityScreen(
                modifier = Modifier.padding(padding)
            )
        }
    }
}

