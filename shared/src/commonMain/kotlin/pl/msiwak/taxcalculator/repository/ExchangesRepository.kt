package pl.msiwak.taxcalculator.repository

import pl.msiwak.taxcalculator.data.Exchange

interface ExchangesRepository {
    suspend fun getExchangeValue(): Exchange
}