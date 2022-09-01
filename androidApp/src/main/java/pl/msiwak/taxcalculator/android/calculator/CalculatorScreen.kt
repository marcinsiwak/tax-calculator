package pl.msiwak.taxcalculator.android.calculator

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import pl.msiwak.taxcalculator.android.R
import pl.msiwak.taxcalculator.android.custom.DateField
import pl.msiwak.taxcalculator.android.custom.DefaultField
import pl.msiwak.taxcalculator.android.custom.FinalValueField
import pl.msiwak.taxcalculator.android.custom.InputField
import pl.msiwak.taxcalculator.android.dialogs.AlertScreen
import pl.msiwak.taxcalculator.android.shapes.CornersEnum
import pl.msiwak.taxcalculator.android.shapes.CustomShape
import pl.msiwak.taxcalculator.android.utlis.extensions.plusBelow
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Operation
import pl.msiwak.taxcalculator.data.OperationType
import pl.msiwak.taxcalculator.data.SortOption
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

@Composable
fun CalculatorScreen() {
    val viewModel = getViewModel<CalculatorViewModel>()
    val state = viewModel.viewState.collectAsState()
    Column(
        Modifier
            .fillMaxHeight()
            .background(color = Color.LightGray)
    ) {
        NewOperationItem(state = state.value, viewModel = viewModel)
        OperationsList(
            modifier = Modifier
                .wrapContentHeight()
                .absoluteOffset(y = 8.dp),
            items = state.value.operations,
            deleteOperation = { viewModel.onUiAction(CalculatorUiAction.OnOperationDeleted(it)) },
            sortAction = { viewModel.onUiAction(CalculatorUiAction.SortList(it)) }
        )
        Row(
            modifier = Modifier
                .offset(y = 8.dp)
                .align(Alignment.End)
        ) {
            FinalValueField(modifier = Modifier.width(100.dp).height(32.dp), text = state.value.incomesValue, shape = CustomShape(CornersEnum.BOTTOM_LEFT))
            FinalValueField(modifier = Modifier.width(100.dp).height(32.dp), text = state.value.outcomesValue, shape = RectangleShape)
            FinalValueField(modifier = Modifier.width(100.dp).height(32.dp), text = state.value.finalValue, shape = CustomShape(CornersEnum.BOTTOM_RIGHT))
        }

        AlertScreen(
            title = "Niepoprawna data",
            description = "Giełda nie pracuje w weekend",
            isOpened = state.value.isAlertShown,
            onDismiss = {
                viewModel.onUiAction(CalculatorUiAction.HideAlert)
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OperationsList(
    modifier: Modifier = Modifier,
    items: List<Operation>,
    deleteOperation: (Operation?) -> Unit,
    sortAction: ((SortOption) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier
            .height(400.dp)
            .background(
                color = Color.White,
                shape = CustomShape(CornersEnum.NO_BOTTOM_RIGHT)
            )
    ) {
        stickyHeader {
            OperationItem(sortAction = sortAction)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
                    .background(Color.Black)
            )
        }
        itemsIndexed(items) { _, operation ->
            OperationItem(operation = operation, deleteOperation = deleteOperation)
        }
    }
}

@Composable
fun OperationItem(
    modifier: Modifier = Modifier,
    operation: Operation? = null,
    deleteOperation: ((Operation?) -> Unit)? = null,
    sortAction: ((SortOption) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(100.dp)
                .padding(8.dp)
                .clickable { sortAction?.invoke(SortOption.Amount()) },
            text = operation?.run {
                amount.toString().replace("-", "")
                    .plusBelow("(${currency.name})")
            } ?: "Amount",
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Black)
        )
        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(8.dp)
                .clickable { sortAction?.invoke(SortOption.Rate()) },
            text = operation?.run {
                date.toString().plusBelow(exchange.toString())
            } ?: "Rate",
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Black)
        )
        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(8.dp)
                .clickable { sortAction?.invoke(SortOption.ExchangedValue()) },
            text = operation?.run {
                exchangedValue.toString().replace("-", "").plusBelow("(PLN)")
            } ?: "Exchanged",
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Black)
        )
        val color = when (operation?.operationType) {
            OperationType.BUY -> {
                Color.Green
            }
            OperationType.SELL -> {
                Color.Yellow
            }
            else -> {
                Color.White
            }
        }

        Text(
            modifier = Modifier
                .width(68.dp)
                .align(Alignment.CenterVertically)
                .padding(8.dp)
                .clickable { sortAction?.invoke(SortOption.Type()) },
            text = operation?.run {
                operationType.name
            } ?: "Type",
            textAlign = TextAlign.Center
        )

        if (operation?.operationType != null) {

            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "button",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(10.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .clickable { deleteOperation?.invoke(operation) },
            )
        }
    }
}

@Composable
fun NewOperationItem(
    modifier: Modifier = Modifier,
    state: CalculatorViewState,
    viewModel: CalculatorViewModel
) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            InputField(
                modifier = Modifier
                    .padding(8.dp)
                    .width(126.dp)
                    .height(36.dp),
                text = state.currencyValue,
                valueChangedCallback = {
                    viewModel.onUiAction(
                        CalculatorUiAction.InputValueChanged(
                            it
                        )
                    )
                })

            CurrencyDropDownMenu(
                modifier = Modifier.padding(8.dp),
                state = state,
                items = Currency.values().toList(),
                onSelectedItemClick = {
                    viewModel.onUiAction(
                        CalculatorUiAction.OnCurrencySelected(
                            it
                        )
                    )
                },
                fieldClickCallback = { viewModel.onUiAction(CalculatorUiAction.OnCurrencyFieldClicked) },
                dismissCallback = { viewModel.onUiAction(CalculatorUiAction.OnDropDownDismiss) }
            )
            DateField(
                modifier = Modifier.padding(8.dp),
                text = "${state.date}",
                isCalendarVisible = state.isCalendarVisible,
                clickCallback = { viewModel.onUiAction(CalculatorUiAction.OnDateClicked) },
                dateCallback = { viewModel.onUiAction(CalculatorUiAction.OnDateSet(it)) },
                dismissCallback = { viewModel.onUiAction(CalculatorUiAction.HideCalendar) })
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            DefaultField(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color.Green)
                    .clickable { viewModel.onUiAction(CalculatorUiAction.OnBuyClicked) },
                text = "BUY"
            )
            DefaultField(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(Color.Yellow)
                    .clickable { viewModel.onUiAction(CalculatorUiAction.OnSellClicked) },
                text = "SELL"
            )
        }
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 2.dp)
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
            modifier = modifier
                .height(36.dp)
                .background(
                    color = Color.White
                )
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable { fieldClickCallback.invoke() },
            text = state.currency.name,
            drawableEndRes = R.drawable.ic_down
        )

        DropdownMenu(
            modifier = Modifier.background(Color.White),
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