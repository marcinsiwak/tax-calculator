package pl.msiwak.taxcalculator.data

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRates(
    val EUR: Double
)