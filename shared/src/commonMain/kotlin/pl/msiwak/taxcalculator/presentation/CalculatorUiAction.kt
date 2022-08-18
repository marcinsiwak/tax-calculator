package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.base.UiAction

sealed class CalculatorUiAction: UiAction {
    class InputValueChanged(val value: String) : CalculatorUiAction()
    class OnDateSet(val date: LocalDate) : CalculatorUiAction()
    class OnCurrencySelected(val currency: String) : CalculatorUiAction()
    object OnCurrencyFieldClicked : CalculatorUiAction()
    object OnDropDownDismiss: CalculatorUiAction()
    object OnDateClicked : CalculatorUiAction()
}