package pl.msiwak.taxcalculator.domain

import pl.msiwak.taxcalculator.data.Exchange
import pl.msiwak.taxcalculator.repository.ExchangesRepository

class GetExchangeUseCase(private val repository: ExchangesRepository) {

    suspend fun invoke(): Exchange {
        return repository.getExchangeValue()
    }
}