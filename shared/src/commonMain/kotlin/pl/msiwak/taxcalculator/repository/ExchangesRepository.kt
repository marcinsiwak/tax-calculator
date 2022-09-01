package pl.msiwak.taxcalculator.repository

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.ExchangeRatesSeries

interface ExchangesRepository {
    suspend fun getExchangeValue(currency: Currency, date: LocalDate): ExchangeRatesSeries
}