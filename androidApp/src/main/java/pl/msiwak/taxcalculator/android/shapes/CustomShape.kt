package pl.msiwak.taxcalculator.android.shapes

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomShape(private val cornersEnum: CornersEnum) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = when(cornersEnum) {
            CornersEnum.BOTTOM_LEFT -> drawBottomLeftRoundedPath(size)
            CornersEnum.BOTTOM_RIGHT -> drawBottomRightRoundedPath(size)
            CornersEnum.NO_BOTTOM_RIGHT -> drawNoBottomRightRoundedPath(size)
        }
        return Outline.Generic(path)
    }


    private fun drawBottomLeftRoundedPath(size: Size): Path {
        val cornerRadius = CornerRadius(20f, 20f)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(0f, 0f),
                        size = size,
                    ),
                    bottomLeft = cornerRadius,
                )
            )
        }
        return path

    }

    private fun drawBottomRightRoundedPath(size: Size): Path {
        val cornerRadius = CornerRadius(20f, 20f)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(0f, 0f),
                        size = size,
                    ),
                    bottomRight = cornerRadius,
                )
            )
        }
        return path
    }

    private fun drawNoBottomRightRoundedPath(size: Size): Path {
        val cornerRadius = CornerRadius(20f, 20f)

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        offset = Offset(0f, 0f),
                        size = size,
                    ),
                    bottomLeft = cornerRadius,
                    topLeft = cornerRadius,
                    topRight = cornerRadius
                )
            )
        }
        return path
    }
}



