package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    text: String,
    valueChangedCallback: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp, 8.dp),
        singleLine = true,
        maxLines = 1,
        value = text,
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
        onValueChange = { valueChangedCallback.invoke(it) }
    )
}