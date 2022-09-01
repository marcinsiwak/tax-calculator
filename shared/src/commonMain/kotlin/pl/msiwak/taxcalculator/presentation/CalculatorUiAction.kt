package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.SortOption
import pl.msiwak.taxcalculator.data.Operation

sealed class CalculatorUiAction {
    class InputValueChanged(val value: String) : CalculatorUiAction()
    class OnDateSet(val date: LocalDate) : CalculatorUiAction()
    class OnCurrencySelected(val currency: Currency) : CalculatorUiAction()
    class OnOperationDeleted(val operation: Operation?) : CalculatorUiAction()
    object OnCurrencyFieldClicked : CalculatorUiAction()
    object OnDropDownDismiss : CalculatorUiAction()
    object OnDateClicked : CalculatorUiAction()
    object OnBuyClicked : CalculatorUiAction()
    object OnSellClicked : CalculatorUiAction()
    object HideCalendar : CalculatorUiAction()
    object HideAlert : CalculatorUiAction()
    class SortList(val sortOption: SortOption) : CalculatorUiAction()
}