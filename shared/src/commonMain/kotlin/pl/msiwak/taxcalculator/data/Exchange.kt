package pl.msiwak.taxcalculator.data

import kotlinx.serialization.Serializable

@Serializable
data class Exchange(
    val base: String,
    val exchange_rates: ExchangeRates,
    val last_updated: String
)