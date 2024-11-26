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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chat.lazycolumn.ui.theme.LazyColumnTheme
import kotlinx.coroutines.launch

class PathActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                PathFollowScreen()
            }
        }
    }
}
@Composable
fun SteamingTeaCup() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Draw the cup
        val multiply = 4
        val cupPath = Path().apply {
            moveTo(50f * multiply, 200f * multiply)
            lineTo(150f * multiply, 200f * multiply)
            cubicTo(160f * multiply, 200f * multiply, 170f * multiply, 190f * multiply, 170f * multiply, 180f * multiply) // Right curve
            lineTo(170f * multiply, 120f * multiply)
            cubicTo(170f * multiply, 110f * multiply, 160f * multiply, 100f * multiply, 150f * multiply, 100f * multiply) // Right bottom curve
            lineTo(50f * multiply, 100f * multiply)
            cubicTo(40f * multiply, 100f * multiply, 30f * multiply, 110f * multiply, 30f * multiply, 120f * multiply) // Left bottom curve
            lineTo(30f * multiply, 180f * multiply)
            cubicTo(30f * multiply, 190f * multiply, 40f * multiply, 200f * multiply, 50f * multiply, 200f * multiply) // Left curve
        }

        // Draw the cup body
        drawPath(path = cupPath, color = Color.Black,style = Stroke(width = 2.dp.toPx()))

        // Draw steam above the cup
        drawLine(
            color = Color.Gray,
            start = Offset(80f * multiply, 70f * multiply),
            end = Offset(80f * multiply, 50f * multiply),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(100f * multiply, 70f * multiply),
            end = Offset(100f * multiply, 50f * multiply),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Gray,
            start = Offset(120f * multiply, 70f * multiply),
            end = Offset(120f * multiply, 50f * multiply),
            strokeWidth = 4f
        )
    }
}
@Composable
fun PathFollowScreen() {
    val firstPathProgress = remember { Animatable(0f) }
    val secondPathProgress = remember { Animatable(0f) }
    val third = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animate the first path
        firstPathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        )
        // After the first path animation completes, animate the second path
        secondPathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        )
        third.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Cup dimensions
        val cupWidth = width * 0.6f
        val cupHeight = height * 0.3f
        val cupLeft = (width - cupWidth) / 2
        val cupTop = height * 0.6f
        val cupRight = cupLeft + cupWidth
        val cupBottom = cupTop + cupHeight

        val cupPath = Path().apply {
            // Bottom ellipse (base of the cup)
            addOval(Rect(cupLeft, cupBottom - 20f, cupRight, cupBottom))

            // Left and right sides of the cup
            moveTo(cupLeft, cupBottom - 10f)
            lineTo(cupLeft, cupTop)

            moveTo(cupRight, cupBottom - 10f)
            lineTo(cupRight, cupTop)

            // Arc for the top of the cup
            addArc(
                oval = Rect(cupLeft, cupTop - 10f, cupRight, cupTop + 10f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -180f
            )
        }

        val handlePath = Path().apply {
            addArc(
                oval = Rect(
                    cupRight - 40f,
                    cupTop + 30f,
                    cupRight + 40f,
                    cupBottom - 30f
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = -180f
            )
        }
        // Draw steam
        val steamPath = Path().apply {
            // Left steam wave
            moveTo(cupLeft + cupWidth * 0.3f, cupTop - 30f)
            cubicTo(
                x1 = cupLeft + cupWidth * 0.25f,
                y1 = cupTop - 60f,
                x2 = cupLeft + cupWidth * 0.35f,
                y2 = cupTop - 90f,
                x3 = cupLeft + cupWidth * 0.3f,
                y3 = cupTop - 120f
            )

            // Right steam wave
            moveTo(cupLeft + cupWidth * 0.7f, cupTop - 30f)
            cubicTo(
                x1 = cupLeft + cupWidth * 0.75f,
                y1 = cupTop - 60f,
                x2 = cupLeft + cupWidth * 0.65f,
                y2 = cupTop - 90f,
                x3 = cupLeft + cupWidth * 0.7f,
                y3 = cupTop - 120f
            )
        }
//        val firstPathMeasure = PathMeasure().apply { setPath(cupPath, false) }
//        val secondPathMeasure = PathMeasure().apply { setPath(handlePath, false) }
//
//        // Animated paths
//        val animatedFirstPath = Path().apply {
//            firstPathMeasure.getSegment(0f, firstPathMeasure.length * firstPathProgress.value, this, true)
//        }
//        val animatedSecondPath = Path().apply {
//            secondPathMeasure.getSegment(0f, secondPathMeasure.length * secondPathProgress.value, this, true)
//        }
//
//        // Draw the paths
//        drawPath(
//            path = animatedFirstPath,
//            color = Color.Blue,
//            style = Stroke(width = 8.dp.toPx())
//        )
//        drawPath(
//            path = animatedSecondPath,
//            color = Color.Red,
//            style = Stroke(width = 8.dp.toPx())
//        )
//        drawPath(
//            path = steamPath,
//            color = Color.LightGray,
//            style = Stroke(width = 4.dp.toPx())
//        )
        drawAnimatedPath(cupPath,firstPathProgress.value,Color.Blue)
        drawAnimatedPath(handlePath,secondPathProgress.value,Color.Blue)
        drawAnimatedPath(steamPath,third.value,Color.Blue)
    }
}



@Preview
@Composable
private fun PathFollowScreenPreview() {
    LazyColumnTheme {
        PathFollowScreen()
    }
}