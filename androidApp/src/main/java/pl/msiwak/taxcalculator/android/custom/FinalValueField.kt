package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import pl.msiwak.taxcalculator.android.shapes.CustomShape

@Composable
fun FinalValueField(modifier: Modifier = Modifier, text: String, shape: Shape) {
    DefaultField(
        modifier = modifier
            .background(
                color = Color.White,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = shape
            ),
        text = text
    )
}