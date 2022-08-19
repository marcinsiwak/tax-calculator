package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StaticField(modifier: Modifier = Modifier, text: String) {
    DefaultField(
        modifier = modifier
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = CircleShape
            ),
        text = text
    )
}