package com.chat.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chat.lazycolumn.ui.theme.LazyColumnTheme
import kotlinx.coroutines.delay


class CoffeeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                Scaffold { padding ->
                    CoffeeCupCanvas(Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun CoffeeCupCanvas(modifier: Modifier) {
    val textMeasurer = rememberTextMeasurer()
    val cupPathProgress = remember { Animatable(0f) }
    val handlePathProgress = remember { Animatable(0f) }
    val steamPathProgress = remember { Animatable(0f) }

    var usedCoffeeAmount by remember { mutableIntStateOf(100) }

    val fillPercentage by animateFloatAsState(
        targetValue = usedCoffeeAmount / 1000f,
        label = "Coffee Fill Animation",
        animationSpec = tween(durationMillis = 4000)
    )

    LaunchedEffect(Unit) {
        cupPathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
        )
        handlePathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
        usedCoffeeAmount = 600
        delay(1000)
        steamPathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val saucerPath = Path().apply {
                val rect = RoundRect(
                    left = canvasWidth * 0.1f,
                    top = canvasHeight * 0.88f,
                    right = canvasWidth * 0.9f,
                    bottom = canvasHeight * 0.9f,
                    cornerRadius = CornerRadius(16f)
                )
                addRoundRect(rect)
                moveTo(rect.left + 2.dp.toPx(), rect.bottom)

                lineTo(canvasWidth * 0.35f, rect.height + canvasHeight - 32.dp.toPx())
                lineTo(canvasWidth * 0.65f, rect.height + canvasHeight - 32.dp.toPx())
                lineTo(rect.right - 2.dp.toPx(), rect.bottom)
                val bottomRect = RoundRect(
                    left = canvasWidth * 0.34f,
                    top = rect.height + canvasHeight - 28.dp.toPx(),
                    right = canvasWidth * 0.66f,
                    bottom = canvasHeight * 0.9f,
                    cornerRadius = CornerRadius(16f)
                )
                addRoundRect(bottomRect)
            }
            drawPath(
                saucerPath, Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8D6E63),
                        Color(0xFF8D6E63),
                        Color(0xFFA59F9F),
                        Color(0xFF5D4037)
                    )
                )
            )

            val cupPath = Path().apply {
                val rect = Rect(
                    left = canvasWidth * 0.25f,
                    top = canvasHeight * 0.5f,
                    right = canvasWidth * 0.75f,
                    bottom = canvasHeight * 0.86f
                )
                moveTo(rect.left + 4.dp.toPx(), rect.top)
                lineTo(rect.right - 4.dp.toPx(), rect.top)

                //right_corner
                quadraticTo(
                    x1 = rect.right, y1 = rect.top,
                    x2 = rect.right, y2 = rect.top + 6.dp.toPx()
                )

                //right
                quadraticTo(
                    x1 = rect.right + 4.dp.toPx(), y1 = rect.bottom,
                    x2 = rect.right - 32.dp.toPx(), y2 = rect.bottom
                )
                //bottom
                lineTo(
                    x = rect.left + 32.dp.toPx(), rect.bottom
                )

                //left
                quadraticTo(
                    x1 = rect.left - 4.dp.toPx(), y1 = rect.bottom - 8.dp.toPx(),
                    x2 = rect.left, y2 = rect.top + 6.dp.toPx()
                )

                //left_corner
                quadraticTo(
                    x1 = rect.left, y1 = rect.top,
                    x2 = rect.left + 4.dp.toPx(), y2 = rect.top
                )

            }

            val handlerPath = Path().apply {
                moveTo(x = canvasWidth * 0.749f, y = canvasHeight * 0.58f)
                cubicTo(
                    x1 = canvasWidth * 0.88f, y1 = canvasHeight * 0.5f,
                    x2 = canvasWidth, y2 = canvasHeight * 0.67f,
                    x3 = canvasWidth * 0.74f, y3 = canvasHeight * 0.75f
                )
            }

            val steamMiddle = Path().apply {
                moveTo(canvasWidth * 0.5f, canvasHeight * 0.48f)
                cubicTo(
                    x1 = canvasWidth * 0.6f, y1 = canvasHeight * 0.4f,
                    x2 = canvasWidth * 0.3f, y2 = canvasHeight * 0.3f,
                    x3 = canvasWidth * 0.5f, y3 = canvasHeight * 0.15f
                )
            }

            val steamRight = Path().apply {
                moveTo(canvasWidth * 0.6f, canvasHeight * 0.48f)
                cubicTo(
                    x1 = canvasWidth * 0.65f, y1 = canvasHeight * 0.4f,
                    x2 = canvasWidth * 0.5f, y2 = canvasHeight * 0.38f,
                    x3 = canvasWidth * 0.59f, y3 = canvasHeight * 0.3f
                )
            }

            val steamLeft = Path().apply {
                moveTo(canvasWidth * 0.4f, canvasHeight * 0.48f)
                cubicTo(
                    x1 = canvasWidth * 0.45f, y1 = canvasHeight * 0.4f,
                    x2 = canvasWidth * 0.3f, y2 = canvasHeight * 0.38f,
                    x3 = canvasWidth * 0.35f, y3 = canvasHeight * 0.3f
                )
            }

            clipPath(cupPath) {
                val waterWavesYPosition = (1 - fillPercentage) * canvasHeight

                val wavePath = Path().apply {
                    moveTo(0f, waterWavesYPosition)
                    for (i in 0 until 6) {
                        val waveWidth = canvasWidth / 5f
                        cubicTo(
                            x1 = waveWidth * i + waveWidth / 2,
                            y1 = waterWavesYPosition - 20f,
                            x2 = waveWidth * i + waveWidth / 2,
                            y2 = waterWavesYPosition + 20f,
                            x3 = waveWidth * (i + 1),
                            y3 = waterWavesYPosition
                        )
                    }
                    lineTo(canvasWidth, canvasHeight)
                    lineTo(0f, canvasHeight)
                    close()
                }
                drawRect(color = Color.Transparent, size = size)

                drawPath(
                    wavePath, brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF6F4E37),
                            Color(0xFFA47551),
                            Color(0xFFD2B48C)
                        ).reversed()
                    )
                )
            }

            val effect = PathEffect.dashPathEffect(floatArrayOf(20f, 30f), phase = 0f)

            val smokeColor = Brush.verticalGradient(
                colors = listOf(
                    Color.Gray.copy(alpha = 0.6f),
                    Color.LightGray.copy(alpha = 0.4f),
                    Color.LightGray.copy(alpha = 0.1f)
                ).reversed()
            )


            drawAnimatedPath(
                handlerPath,
                handlePathProgress.value,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5A3A29),
                        Color(0xFFE0E0E0)
                    )
                ),
                style = Stroke(width = 32f)
            )
            drawAnimatedPath(
                cupPath,
                cupPathProgress.value,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6F4E37),
                        Color(0xFF6F4E37),
                    ).reversed()
                ),
                style = Stroke(width = 8f)
            )
            drawAnimatedPath(
                steamRight,
                steamPathProgress.value,
                brush = smokeColor,
                style = Stroke(width = 16f, pathEffect = effect, cap = StrokeCap.Round)
            )
            drawAnimatedPath(
                steamMiddle,
                steamPathProgress.value,
                brush = smokeColor,
                style = Stroke(width = 16f, pathEffect = effect, cap = StrokeCap.Round)
            )
            drawAnimatedPath(
                steamLeft,
                steamPathProgress.value,
                brush = smokeColor,
                style = Stroke(width = 16f, pathEffect = effect, cap = StrokeCap.Round)
            )
            val rect = Rect(
                left = canvasWidth * 0.25f,
                top = canvasHeight * 0.5f,
                right = canvasWidth * 0.75f,
                bottom = canvasHeight * 0.86f
            )
            val text = "Infinity Programmer"
            val measuredText =
                textMeasurer.measure(
                    AnnotatedString(text),
                    constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                    style = TextStyle(
                        fontSize = 20.sp,
                        brush = Brush.verticalGradient(
                            listOf(Color.White, Color.White.copy(0.8f))
                        ),
                        fontWeight = FontWeight.Bold
                    )
                )

            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(
                    rect.width * 0.5f + 8.dp.toPx(),
                    ((canvasHeight / 2) + rect.height / 2) - 8.dp.toPx()
                ),

                )

        }
    }
}

fun DrawScope.drawAnimatedPath(
    path: Path,
    progress: Float,
    color: Color? = null,
    style: DrawStyle = Fill,
    brush: Brush? = null
) {
    val pathMeasure = PathMeasure().apply { setPath(path, false) }
    val animatedPath = Path().apply {
        pathMeasure.getSegment(0f, pathMeasure.length * progress, this, true)
    }
    if (brush == null) {
        drawPath(
            path = animatedPath,
            color = color!!,
            style = style
        )
    } else {
        drawPath(
            path = animatedPath,
            brush = brush,
            style = style
        )
    }
}
