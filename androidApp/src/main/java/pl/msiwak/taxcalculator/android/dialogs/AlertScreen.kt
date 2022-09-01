package pl.msiwak.taxcalculator.android.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlertScreen(title: String, description: String, isOpened: Boolean, onDismiss: () -> Unit) {

    if (isOpened) {
        AlertDialog(
            onDismissRequest = {
                onDismiss.invoke()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description)
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onDismiss.invoke() }
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    }
}