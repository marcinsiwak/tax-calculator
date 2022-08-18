package pl.msiwak.taxcalculator.android.custom

import android.widget.CalendarView
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Calendar(modifier: Modifier = Modifier, dateCallback: (Int, Int, Int) -> Unit) {
    AndroidView(
        { CalendarView(it) },
        modifier = modifier.wrapContentWidth(),
        update = { views ->
            views.setOnDateChangeListener { _, year, month, day ->
                dateCallback.invoke(year, month, day)
            }
        }
    )
}