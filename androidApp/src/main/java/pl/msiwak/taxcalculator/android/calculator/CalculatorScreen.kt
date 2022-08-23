package pl.msiwak.taxcalculator.android.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import pl.msiwak.taxcalculator.android.R
import pl.msiwak.taxcalculator.android.custom.DateField
import pl.msiwak.taxcalculator.android.custom.DefaultField
import pl.msiwak.taxcalculator.android.custom.InputField
import pl.msiwak.taxcalculator.android.custom.StaticField
import pl.msiwak.taxcalculator.android.utlis.extensions.plusBelow
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Operation
import pl.msiwak.taxcalculator.data.OperationType
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

@Composable
fun CalculatorScreen() {
    val viewModel = getViewModel<CalculatorViewModel>()
    val state = viewModel.viewState.collectAsState()
    Column(Modifier.fillMaxHeight()) {
        NewOperationItem(state = state.value, viewModel = viewModel)
        OperationsList(
            modifier = Modifier
                .wrapContentHeight()
                .absoluteOffset(y = 36.dp), items = state.value.operations
        )
        StaticField(
            modifier = Modifier
                .absoluteOffset(y = 72.dp)
                .align(Alignment.End), text = state.value.finalValue
        )
    }
}

@Composable
fun OperationsList(modifier: Modifier = Modifier, items: List<Operation>) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items) { _, operation ->
            OperationItem(operation = operation)

        }
    }
}

@Composable
fun OperationItem(modifier: Modifier = Modifier, operation: Operation) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(100.dp)
                .padding(8.dp),
            text = operation.price.toString().plusBelow("(${operation.currency.name})"),
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.Black)
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = operation.date.toString().plusBelow(operation.exchange)
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.Black)
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = operation.exchangedValue.toString().plusBelow("(PLN)")
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.Black)
        )
        val color = if (operation.operationType == OperationType.BUY) {
            Color.Green
        } else {
            Color.Yellow
        }
        Text(
            modifier = Modifier
                .background(color)
                .fillMaxHeight()
                .padding(8.dp),
            text = operation.operationType.name,
            textAlign = TextAlign.Center
        )

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(Color.Black)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "button",
            alignment = Alignment.Center,
            modifier = Modifier
                .scale(0.7f)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable { }
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
            items = Currency.values().toList(),
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
    items: List<Currency>,
    fieldClickCallback: () -> Unit,
    dismissCallback: () -> Unit,
    onSelectedItemClick: (Currency) -> Unit
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
            text = state.currency.name
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
                        text = currency.name,
                        color = Color.Black
                    )
                }
            }
        }
    }
}