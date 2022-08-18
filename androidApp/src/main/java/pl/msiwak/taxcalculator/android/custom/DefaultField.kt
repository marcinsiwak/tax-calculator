package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DefaultField(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .padding(16.dp, 8.dp),
        text = text,
        textAlign = TextAlign.Center
    )
}
