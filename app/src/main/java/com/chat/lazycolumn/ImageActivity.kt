package com.chat.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.chat.lazycolumn.ui.theme.LazyColumnTheme
import kotlin.math.roundToInt

class ImageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                ImageListScreen()
            }
        }
    }
}

@Composable
fun HeaderTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        fontSize = 20.sp,
        color = Color.Black,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ImageListScreen() {
    val dogImage = painterResource(R.drawable.dog)

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeaderTitle("Circular Image")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(CircleShape)

                )
                HeaderTitle("Rectangle Image")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))

                )
                HeaderTitle("Oval Image")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(SquashedOval())

                )
                HeaderTitle("Circular Image with stroke")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                        .border(
                            BorderStroke(4.dp, Color.Yellow), CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)

                )
                HeaderTitle("Circular Image with gradient stroke")
                val rainbowColorsBrush = remember {
                    Brush.sweepGradient(
                        listOf(
                            Color(0xFF9575CD),
                            Color(0xFFBA68C8),
                            Color(0xFFE57373),
                            Color(0xFFFFB74D),
                            Color(0xFFFFF176),
                            Color(0xFFAED581),
                            Color(0xFF4DD0E1),
                            Color(0xFF9575CD)
                        )
                    )
                }
                val borderWidth = 4.dp
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                        .border(
                            BorderStroke(borderWidth, rainbowColorsBrush), CircleShape
                        )
                        .padding(borderWidth)
                        .clip(CircleShape)

                )
                HeaderTitle("Green color filter")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.Darken),
                    modifier = Modifier.padding(8.dp)
                )

                HeaderTitle("GreyScale color filter")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }),
                    modifier = Modifier.padding(8.dp)
                )
                HeaderTitle("Color filter")
                val contrast = 2f
                val brightness = -180f
                val colorMatrix = floatArrayOf(
                    contrast,
                    0f,
                    0f,
                    0f,
                    brightness,
                    0f,
                    contrast,
                    0f,
                    0f,
                    brightness,
                    0f,
                    0f,
                    contrast,
                    0f,
                    brightness,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f
                )
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
                    modifier = Modifier.padding(8.dp)
                )
                HeaderTitle("Inverted Color filter")
                val colorMatrixInverted = floatArrayOf(
                    -1f,
                    0f,
                    0f,
                    0f,
                    255f,
                    0f,
                    -1f,
                    0f,
                    0f,
                    255f,
                    0f,
                    0f,
                    -1f,
                    0f,
                    255f,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f
                )
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrixInverted)),
                    modifier = Modifier.padding(8.dp)
                )
                HeaderTitle("Blur filter")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                        )
                )
                HeaderTitle("Blur filter unbounded")
                Image(
                    painter = dogImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(150.dp)
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                        .clip(RoundedCornerShape(8.dp))
                )

                HeaderTitle("Rainbow overlay")

                val rainbowImageBitmap = ImageBitmap.imageResource(id = R.drawable.dog)
                val dogImageBitmap = ImageBitmap.imageResource(id = R.drawable.rainbow)

                val customPainter = remember {
                    OverlayImagePainter(rainbowImageBitmap, dogImageBitmap)
                }
                Image(
                    painter = customPainter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                )

                HeaderTitle("Clip Image with circle")
                Image(painter = dogImage,
                    contentDescription = "Dog",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .aspectRatio(1f)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFC5E1A5), Color(0xFF80DEEA)
                                )
                            )
                        )
                        .padding(8.dp)
                        .graphicsLayer {
                            compositingStrategy = CompositingStrategy.Offscreen
                        }
                        .drawWithCache {
                            val path = Path()
                            path.addOval(
                                Rect(
                                    topLeft = Offset.Zero,
                                    bottomRight = Offset(size.width, size.height)
                                )
                            )
                            onDrawWithContent {
                                clipPath(path) {

                                    this@onDrawWithContent.drawContent()
                                }
                                val dotSize = size.width / 8f

                                drawCircle(
                                    Color.Black, radius = dotSize, center = Offset(
                                        x = size.width - dotSize, y = size.height - dotSize
                                    ), blendMode = BlendMode.Clear
                                )

                                drawCircle(
                                    Color(0xFFEF5350), radius = dotSize * 0.8f, center = Offset(
                                        x = size.width - dotSize, y = size.height - dotSize
                                    )
                                )
                            }
                        })
                ImageText()

            }
        }
    }
}

@Composable
fun ImageText() {

    HeaderTitle("ImageShader Brush ")
    val imageBrush = ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.dog)))
    Text(
        text = "Hello Android!", style = TextStyle(
            brush = imageBrush, fontWeight = FontWeight.ExtraBold, fontSize = 36.sp
        )
    )

}

@Preview
@Composable
private fun ImageListScreenPreview() {
    LazyColumnTheme {
        ImageListScreen()
    }
}

class SquashedOval : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        val path = Path().apply {
            addOval(
                Rect(
                    left = size.width / 4f,
                    top = 0f,
                    right = size.width * 3 / 4f,
                    bottom = size.height
                )
            )
        }
        return Outline.Generic(path = path)
    }
}


class OverlayImagePainter(
    private val image: ImageBitmap,
    private val imageOverlay: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height),
    private val overlaySize: IntSize = IntSize(imageOverlay.width, imageOverlay.height)
) : Painter() {

    private val size: IntSize = validateSize(srcOffset, srcSize)
    override fun DrawScope.onDraw() {
        drawImage(
            image, srcOffset, srcSize, dstSize = IntSize(
                this@onDraw.size.width.roundToInt(), this@onDraw.size.height.roundToInt()
            )
        )
        drawImage(
            imageOverlay, srcOffset, overlaySize, dstSize = IntSize(
                this@onDraw.size.width.roundToInt(), this@onDraw.size.height.roundToInt()
            ), blendMode = BlendMode.Overlay
        )
    }

    override val intrinsicSize: Size get() = size.toSize()

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 && srcOffset.y >= 0 && srcSize.width >= 0 && srcSize.height >= 0 && srcSize.width <= image.width && srcSize.height <= image.height
        )
        return srcSize
    }
}
