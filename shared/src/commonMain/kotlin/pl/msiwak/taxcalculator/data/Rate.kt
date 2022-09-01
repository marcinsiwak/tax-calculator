package pl.msiwak.taxcalculator.data

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
class Rate(
    val no: String,
    val effectiveDate: LocalDate,
    val mid: Double
)
