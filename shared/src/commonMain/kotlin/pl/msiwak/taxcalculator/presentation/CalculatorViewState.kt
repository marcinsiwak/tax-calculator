package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Operation

data class CalculatorViewState(
    val currency: Currency = Currency.USD,
    val currencyValue: String = "",
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val isCalendarVisible: Boolean = false,
    val isDropDownMenuVisible: Boolean = false,
    val operations: List<Operation> = emptyList(),
    val incomesValue: String = "",
    val outcomesValue: String = "",
    val finalValue: String = "",
    val isAlertShown: Boolean = false
)