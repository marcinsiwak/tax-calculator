package pl.msiwak.taxcalculator.presentation

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class CalculatorViewState(
    val currency: String = "PLN",
    val currencyValue: String = "",
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val isCalendarVisible: Boolean = false,
    val isDropDownMenuVisible: Boolean = false
)