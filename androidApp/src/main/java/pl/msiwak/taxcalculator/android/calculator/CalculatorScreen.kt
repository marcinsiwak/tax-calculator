package pl.msiwak.taxcalculator.android.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.getViewModel
import pl.msiwak.taxcalculator.android.custom.Calendar
import pl.msiwak.taxcalculator.android.custom.DateField
import pl.msiwak.taxcalculator.android.custom.DefaultField
import pl.msiwak.taxcalculator.android.custom.InputField
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

@Composable
fun CalculatorScreen() {
    val viewModel = getViewModel<CalculatorViewModel>()
    val state = viewModel.viewState.collectAsState()
    Column {
        OperationItem(state = state.value, viewModel = viewModel)

        if (state.value.isCalendarVisible) {
            Calendar(dateCallback = { year, month, day ->
                viewModel.onUiAction(
                    CalculatorUiAction.OnDateSet(
                        LocalDate(year, month, day)
                    )
                )
            })
        }
    }
}

@Composable
fun OperationItem(
    modifier: Modifier = Modifier,
    state: CalculatorViewState,
    viewModel: CalculatorViewModel
) {
    Row(modifier = modifier) {
        InputField(
            text = state.currencyValue,
            valueChangedCallback = { viewModel.onUiAction(CalculatorUiAction.InputValueChanged(it)) })
        DateField(
            text = "${state.date}",
            clickCallback = { viewModel.onUiAction(CalculatorUiAction.OnDateClicked) })

        CurrencyDropDownMenu(
            state = state,
            items = listOf("PLN", "USD"),
            onSelectedItemClick = { viewModel.onUiAction(CalculatorUiAction.OnCurrencySelected(it)) },
            fieldClickCallback = { viewModel.onUiAction(CalculatorUiAction.OnCurrencyFieldClicked) },
            dismissCallback = { viewModel.onUiAction(CalculatorUiAction.OnDropDownDismiss) }
        )
        DefaultField(modifier = modifier.background(Color.Green, shape = CircleShape), text = "BUY")
        DefaultField(
            modifier = modifier.background(Color.Yellow, shape = CircleShape),
            text = "SELL"
        )

    }
}

@Composable
fun CurrencyDropDownMenu(
    modifier: Modifier = Modifier,
    state: CalculatorViewState,
    items: List<String>,
    fieldClickCallback: () -> Unit,
    dismissCallback: () -> Unit,
    onSelectedItemClick: (String) -> Unit
) {
    Column {
        DefaultField(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
                .clickable { fieldClickCallback.invoke() },
            text = state.currency
        )

        DropdownMenu(
            modifier = modifier.background(Color.White),
            expanded = state.isDropDownMenuVisible,
            onDismissRequest = { dismissCallback.invoke() }
        ) {
            items.forEach { currency ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedItemClick(currency)
                    }
                ) {
                    Text(
                        text = currency,
                        color = Color.Black
                    )
                }
            }
        }
    }
}