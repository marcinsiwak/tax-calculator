package pl.msiwak.taxcalculator.api

import pl.msiwak.taxcalculator.data.Exchange

interface ExchangesApi {
    suspend fun getExchangeValue(): Exchange
}