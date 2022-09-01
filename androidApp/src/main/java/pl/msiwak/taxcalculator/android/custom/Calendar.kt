package pl.msiwak.taxcalculator.android.custom

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.msiwak.taxcalculator.android.R

@Composable
fun Calendar(modifier: Modifier = Modifier, dateCallback: (Int, Int, Int) -> Unit, dismissCallback: () -> Unit) {
    Dialog(onDismissRequest = { dismissCallback.invoke() }) {
        Column(modifier = Modifier
            .wrapContentSize()
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
        ) {
            AndroidView(
                { CalendarView(ContextThemeWrapper(it, R.style.CalenderViewCustom)) },
                modifier = modifier.wrapContentWidth(),
                update = { views ->
                    views.setOnDateChangeListener { _, year, month, day ->
                        dateCallback.invoke(year, month + 1, day)
                    }
                }
            )
        }
    }
}