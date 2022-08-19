package pl.msiwak.taxcalculator.repository

import pl.msiwak.taxcalculator.api.ExchangesApi
import pl.msiwak.taxcalculator.data.Exchange

class ExchangesRepositoryImpl(private val exchangesApi: ExchangesApi) : ExchangesRepository {

    override suspend fun getExchangeValue(): Exchange {
        return exchangesApi.getExchangeValue()
    }
}