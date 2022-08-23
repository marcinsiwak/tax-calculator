package pl.msiwak.taxcalculator.data

import kotlinx.datetime.LocalDate

class Operation(
    val price: Double,
    val date: LocalDate,
    val currency: Currency,
    val exchange: String,
    val exchangedValue: Double,
    val operationType: OperationType,
)