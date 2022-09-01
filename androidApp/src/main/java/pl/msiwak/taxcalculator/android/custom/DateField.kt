package pl.msiwak.taxcalculator.android.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun DateField(
    modifier: Modifier = Modifier,
    text: String,
    isCalendarVisible: Boolean,
    clickCallback: () -> Unit,
    dateCallback: (LocalDate) -> Unit,
    dismissCallback: () -> Unit
) {
    DefaultField(
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
            .clickable { clickCallback.invoke() },
        text = text
    )


    if (isCalendarVisible) {
        Calendar(dateCallback = { year, month, day ->
            dateCallback.invoke(LocalDate(year, month, day))
        },
            dismissCallback = {
                dismissCallback.invoke()
            })
    }
}