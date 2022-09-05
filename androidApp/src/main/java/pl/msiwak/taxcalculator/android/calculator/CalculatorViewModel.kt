package pl.msiwak.taxcalculator.android.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.minus
import pl.msiwak.taxcalculator.data.Operation
import pl.msiwak.taxcalculator.data.OperationType
import pl.msiwak.taxcalculator.data.SortOption
import pl.msiwak.taxcalculator.domain.GetExchangeUseCase
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState
import timber.log.Timber

class CalculatorViewModel(private val getExchangeUseCase: GetExchangeUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow(CalculatorViewState())
    val viewState: StateFlow<CalculatorViewState>
        get() = _viewState

    private var currentType: SortOption = SortOption.Rate()

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        Timber.e(e, e.message)
    }

    fun onUiAction(action: CalculatorUiAction) {
        when (action) {
            is CalculatorUiAction.InputValueChanged -> onValueChanged(action)
            is CalculatorUiAction.OnDateSet -> onDateChanged(action)
            is CalculatorUiAction.OnCurrencySelected -> onCurrencySelected(action)
            is CalculatorUiAction.OnOperationDeleted -> onOperationDeleted(action)
            CalculatorUiAction.OnDateClicked -> onDateClicked()
            CalculatorUiAction.OnCurrencyFieldClicked -> onCurrencyFieldClicked()
            CalculatorUiAction.OnDropDownDismiss -> onDropDownDismiss()
            CalculatorUiAction.OnBuyClicked -> onBuyClicked()
            CalculatorUiAction.OnSellClicked -> onSellClicked()
            CalculatorUiAction.HideCalendar -> onHideCalendar()
            CalculatorUiAction.HideAlert -> onHideAlert()
            is CalculatorUiAction.SortList -> onListSorted(action)
        }
    }

    private fun onListSorted(action: CalculatorUiAction.SortList) {
        val newList = mutableListOf<Operation>()
        newList.addAll(_viewState.value.operations)
        when (action.sortOption) {
            is SortOption.Amount -> {
                currentType =
                    if (currentType is SortOption.Amount && !(currentType as SortOption.Amount).isDescending) {
                        newList.sortByDescending { it.amount }
                        SortOption.Amount(true)
                    } else {
                        newList.sortBy { it.amount }
                        SortOption.Amount(false)
                    }
            }
            is SortOption.Rate -> {
                currentType =
                    if (currentType is SortOption.Rate && !(currentType as SortOption.Rate).isDescending) {
                        newList.sortByDescending { it.exchange }
                        SortOption.Rate(true)
                    } else {
                        newList.sortBy { it.exchange }
                        SortOption.Rate(false)
                    }
            }
            is SortOption.ExchangedValue -> {
                currentType =
                    if (currentType is SortOption.ExchangedValue && !(currentType as SortOption.ExchangedValue).isDescending) {
                        newList.sortByDescending { it.exchangedValue }
                        SortOption.ExchangedValue(true)
                    } else {
                        newList.sortBy { it.exchangedValue }
                        SortOption.ExchangedValue(false)
                    }
            }
            is SortOption.Type -> {
                currentType =
                    if (currentType is SortOption.Type && !(currentType as SortOption.Type).isBuy) {
                        newList.sortByDescending { it.operationType }
                        SortOption.Type(true)
                    } else {
                        newList.sortBy { it.operationType }
                        SortOption.Type(false)
                    }
            }
        }
        _viewState.value = _viewState.value.copy(operations = newList)
    }

    private fun onHideCalendar() {
        _viewState.value = _viewState.value.copy(isCalendarVisible = false)
    }

    private fun onHideAlert() {
        _viewState.value = _viewState.value.copy(isAlertShown = false)
    }

    private fun onBuyClicked() {
        actionOnOperation(OperationType.BUY)
    }

    private fun onSellClicked() {
        actionOnOperation(OperationType.SELL)
    }

    private fun actionOnOperation(operationType: OperationType) {
        val currency = _viewState.value.currency
        val date = _viewState.value.date

        if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            _viewState.value = _viewState.value.copy(isAlertShown = true)
            return
        }

        viewModelScope.launch(errorHandler) {
            val output = getExchangeUseCase.invoke(currency, date)
            val exchangeRate = output.rates[0].mid
            val operation = _viewState.value.let {
                val finalAmount = if (operationType == OperationType.SELL) {
                    it.currencyValue.toDouble()
                } else {
                    it.currencyValue.toDouble().unaryMinus()
                }
                Operation(
                    amount = finalAmount,
                    date = it.date.minus(1, DateTimeUnit.DAY),
                    currency = it.currency,
                    operationType = operationType,
                    exchange = exchangeRate,
                    exchangedValue = calculateExchangedValue(
                        finalAmount,
                        exchangeRate
                    )
                )
            }
            val newList = mutableListOf<Operation>()
            newList.addAll(_viewState.value.operations)
            newList.add(operation)

            _viewState.value = _viewState.value.copy(
                operations = newList,
                outcomesValue = newList.filter { it.exchangedValue < 0 }.sumOf { it.exchangedValue }
                    .unaryMinus()
                    .toString(),
                incomesValue = newList.filter { it.exchangedValue > 0 }.sumOf { it.exchangedValue }
                    .toString(),
                finalValue = newList.sumOf { it.exchangedValue }
                    .toString())
        }
    }

    private fun calculateExchangedValue(amount: Double, exchangeRate: Double): Double {
        return amount * exchangeRate
    }

    private fun onDropDownDismiss() {
        _viewState.value = _viewState.value.copy(isDropDownMenuVisible = false)
    }

    private fun onCurrencyFieldClicked() {
        _viewState.value =
            _viewState.value.copy(isDropDownMenuVisible = !_viewState.value.isDropDownMenuVisible)
    }

    private fun onValueChanged(action: CalculatorUiAction.InputValueChanged) {
        _viewState.value = _viewState.value.copy(currencyValue = action.value)
    }

    private fun onDateChanged(action: CalculatorUiAction.OnDateSet) {
        _viewState.value = _viewState.value.copy(
            date = action.date,
            isCalendarVisible = !_viewState.value.isCalendarVisible
        )
    }

    private fun onDateClicked() {
        _viewState.value =
            _viewState.value.copy(isCalendarVisible = !_viewState.value.isCalendarVisible)
    }

    private fun onCurrencySelected(action: CalculatorUiAction.OnCurrencySelected) {
        _viewState.value =
            _viewState.value.copy(currency = action.currency, isDropDownMenuVisible = false)
    }

    private fun onOperationDeleted(action: CalculatorUiAction.OnOperationDeleted) {
        val newList = mutableListOf<Operation>()
        newList.addAll(_viewState.value.operations)
        newList.remove(action.operation)

        _viewState.value = _viewState.value.copy(
            operations = newList,
            finalValue = newList.sumOf { it.exchangedValue }
                .toString())
    }
}