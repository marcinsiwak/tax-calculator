package pl.msiwak.taxcalculator.android.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import pl.msiwak.taxcalculator.android.custom.DateField
import pl.msiwak.taxcalculator.android.custom.DefaultField
import pl.msiwak.taxcalculator.android.custom.InputField
import pl.msiwak.taxcalculator.android.custom.StaticField
import pl.msiwak.taxcalculator.data.Operation
import pl.msiwak.taxcalculator.data.OperationType
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

@Composable
fun CalculatorScreen() {
    val viewModel = getViewModel<CalculatorViewModel>()
    val state = viewModel.viewState.collectAsState()
    Column {
        NewOperationItem(state = state.value, viewModel = viewModel)
        OperationsList(items = state.value.operations)

    }
}

@Composable
fun OperationsList(modifier: Modifier = Modifier, items: List<Operation>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        itemsIndexed(items) { index, operation ->
            OperationItem(operation = operation)

        }
    }
}

@Composable
fun OperationItem(modifier: Modifier = Modifier, operation: Operation) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        StaticField(text = operation.price.toString())
        StaticField(text = operation.date.toString())
        StaticField(text = operation.currency)
        StaticField(text = operation.exchange)
        val color = if (operation.operationType == OperationType.BUY) {
            Color.Green
        } else {
            Color.Yellow
        }
        DefaultField(
            modifier = Modifier.background(color, shape = CircleShape),
            text = operation.operationType.name
        )
    }
}

@Composable
fun NewOperationItem(
    modifier: Modifier = Modifier,
    state: CalculatorViewState,
    viewModel: CalculatorViewModel
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        InputField(
            text = state.currencyValue,
            valueChangedCallback = { viewModel.onUiAction(CalculatorUiAction.InputValueChanged(it)) })
        DateField(
            text = "${state.date}",
            isCalendarVisible = state.isCalendarVisible,
            clickCallback = { viewModel.onUiAction(CalculatorUiAction.OnDateClicked) },
            dateCallback = { viewModel.onUiAction(CalculatorUiAction.OnDateSet(it)) })

        CurrencyDropDownMenu(
            state = state,
            items = listOf("PLN", "USD"),
            onSelectedItemClick = { viewModel.onUiAction(CalculatorUiAction.OnCurrencySelected(it)) },
            fieldClickCallback = { viewModel.onUiAction(CalculatorUiAction.OnCurrencyFieldClicked) },
            dismissCallback = { viewModel.onUiAction(CalculatorUiAction.OnDropDownDismiss) }
        )
        DefaultField(
            modifier = modifier
                .background(Color.Green, shape = CircleShape)
                .clickable { viewModel.onUiAction(CalculatorUiAction.OnBuyClicked) },
            text = "BUY"
        )
        DefaultField(
            modifier = modifier
                .background(Color.Yellow, shape = CircleShape)
                .clickable { viewModel.onUiAction(CalculatorUiAction.OnSellClicked) },
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