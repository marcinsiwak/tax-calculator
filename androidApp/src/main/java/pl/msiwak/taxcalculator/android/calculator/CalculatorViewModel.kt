package pl.msiwak.taxcalculator.android.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.msiwak.taxcalculator.presentation.CalculatorUiAction
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

class CalculatorViewModel : ViewModel() {

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
        }
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