package pl.msiwak.taxcalculator.android.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.msiwak.taxcalculator.data.Operation
import pl.msiwak.taxcalculator.data.OperationType
import pl.msiwak.taxcalculator.domain.GetExchangeUseCase
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

class CalculatorViewModel(val useCase: GetExchangeUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow(CalculatorViewState())
    val viewState: StateFlow<CalculatorViewState>
        get() = _viewState

    fun onUiAction(action: CalculatorUiAction) {
        when(action){
            is CalculatorUiAction.InputValueChanged -> onValueChanged(action)
            is CalculatorUiAction.OnDateSet -> onDateChanged(action)
            is CalculatorUiAction.OnCurrencySelected -> onCurrencySelected(action)
            CalculatorUiAction.OnDateClicked -> onDateClicked()
            CalculatorUiAction.OnCurrencyFieldClicked -> onCurrencyFieldClicked()
            CalculatorUiAction.OnDropDownDismiss -> onDropDownDismiss()
            CalculatorUiAction.OnBuyClicked -> onBuyClicked()
            CalculatorUiAction.OnSellClicked -> onSellClicked()
        }
    }

    private fun onBuyClicked() {
        val operation =_viewState.value.let {
            Operation(price = it.currencyValue.toDouble(),
            date = it.date,
            currency = it.currency,
            operationType = OperationType.BUY,
            exchange = "")
        }
        val newList = mutableListOf<Operation>()
        newList.addAll(_viewState.value.operations)
        newList.add(operation)

        viewModelScope.launch {
            val output = useCase.invoke().exchange_rates.EUR
            Log.e("OUTPUT, ","$output")
        }
        _viewState.value = _viewState.value.copy(operations = newList)
    }

    private fun onSellClicked() {
        val operation =_viewState.value.let {
            Operation(price = it.currencyValue.toDouble(),
            date = it.date,
            currency = it.currency,
            operationType = OperationType.SELL,
            exchange = "")
        }
        val newList = mutableListOf<Operation>()
        newList.addAll(_viewState.value.operations)
        newList.add(operation)
        _viewState.value = _viewState.value.copy(operations = newList)
    }

    private fun onDropDownDismiss() {
        _viewState.value = _viewState.value.copy(isDropDownMenuVisible = false)
    }

    private fun onCurrencyFieldClicked() {
        _viewState.value = _viewState.value.copy(isDropDownMenuVisible = !_viewState.value.isDropDownMenuVisible)
    }

    private fun onValueChanged(action: CalculatorUiAction.InputValueChanged) {
        _viewState.value = _viewState.value.copy(currencyValue = action.value)
    }

    private fun onDateChanged(action: CalculatorUiAction.OnDateSet) {
        _viewState.value = _viewState.value.copy(date = action.date, isCalendarVisible = !_viewState.value.isCalendarVisible)
    }

    private fun onDateClicked() {
        _viewState.value = _viewState.value.copy(isCalendarVisible = !_viewState.value.isCalendarVisible)
    }

    private fun onCurrencySelected(action: CalculatorUiAction.OnCurrencySelected) {
        _viewState.value = _viewState.value.copy(currency = action.currency, isDropDownMenuVisible = false)
    }
}