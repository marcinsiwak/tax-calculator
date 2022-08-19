package pl.msiwak.taxcalculator.data

import kotlinx.datetime.LocalDate

class Operation(
    val price: Double,
    val date: LocalDate,
    val currency: String,
    val exchange: String,
    val operationType: OperationType
)