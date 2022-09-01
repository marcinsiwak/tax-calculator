package pl.msiwak.taxcalculator.repository

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import pl.msiwak.taxcalculator.api.ExchangesApi
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.ExchangeRatesSeries

class ExchangesRepositoryImpl(private val exchangesApi: ExchangesApi) : ExchangesRepository {

    override suspend fun getExchangeValue(
        currency: Currency,
        date: LocalDate
    ): ExchangeRatesSeries {
        val finalDate = if (date.dayOfWeek == DayOfWeek.MONDAY) {
            date.minus(3, DateTimeUnit.DAY)
        } else {
            date.minus(1, DateTimeUnit.DAY)
        }
        return exchangesApi.getExchangeValue(currency, finalDate)
    }
}