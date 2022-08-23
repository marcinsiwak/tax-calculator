package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency

sealed class CalculatorUiAction {
    class InputValueChanged(val value: String) : CalculatorUiAction()
    class OnDateSet(val date: LocalDate) : CalculatorUiAction()
    class OnCurrencySelected(val currency: Currency) : CalculatorUiAction()
    object OnCurrencyFieldClicked : CalculatorUiAction()
    object OnDropDownDismiss : CalculatorUiAction()
    object OnDateClicked : CalculatorUiAction()
    object OnBuyClicked : CalculatorUiAction()
    object OnSellClicked : CalculatorUiAction()
}