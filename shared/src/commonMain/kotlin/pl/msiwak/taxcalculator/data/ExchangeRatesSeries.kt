package pl.msiwak.taxcalculator.data

import kotlinx.serialization.Serializable

@Serializable
class ExchangeRatesSeries(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<Rate>
)