package pl.msiwak.taxcalculator.data

import kotlinx.datetime.LocalDate

class Operation(
    val amount: Double,
    val date: LocalDate,
    val currency: Currency,
    val exchange: Double,
    val exchangedValue: Double,
    val operationType: OperationType,
)