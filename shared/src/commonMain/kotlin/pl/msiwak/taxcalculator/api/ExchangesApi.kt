package pl.msiwak.taxcalculator.api

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.ExchangeRatesSeries

interface ExchangesApi {
    suspend fun getExchangeValue(currency: Currency, date: LocalDate): ExchangeRatesSeries


}