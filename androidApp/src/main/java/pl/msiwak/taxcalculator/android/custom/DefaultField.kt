package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DefaultField(
    modifier: Modifier = Modifier,
    text: String,
    drawableEndRes: Int? = null
) {
    Row(
        modifier = modifier
            .padding(8.dp, 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
        drawableEndRes?.let {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = it),
                contentDescription = "down"
            )
        }
    }
}
