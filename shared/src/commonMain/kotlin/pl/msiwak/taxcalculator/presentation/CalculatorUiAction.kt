package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.LocalDate

sealed class CalculatorUiAction {
    class InputValueChanged(val value: String) : CalculatorUiAction()
    class OnDateSet(val date: LocalDate) : CalculatorUiAction()
    class OnCurrencySelected(val currency: String) : CalculatorUiAction()
    object OnCurrencyFieldClicked : CalculatorUiAction()
    object OnDropDownDismiss : CalculatorUiAction()
    object OnDateClicked : CalculatorUiAction()
    object OnBuyClicked : CalculatorUiAction()
    object OnSellClicked : CalculatorUiAction()
}