package pl.msiwak.taxcalculator.repository

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.api.ExchangesApi
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Exchange

class ExchangesRepositoryImpl(private val exchangesApi: ExchangesApi) : ExchangesRepository {

    override suspend fun getExchangeValue(currency: Currency, date: LocalDate): Exchange {
        return exchangesApi.getExchangeValue(currency, date)
    }
}