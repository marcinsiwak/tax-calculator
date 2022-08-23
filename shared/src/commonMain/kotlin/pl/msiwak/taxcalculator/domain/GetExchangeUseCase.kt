package pl.msiwak.taxcalculator.domain

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Exchange
import pl.msiwak.taxcalculator.repository.ExchangesRepository

class GetExchangeUseCase(private val repository: ExchangesRepository) {

    suspend fun invoke(currency: Currency, date: LocalDate): Exchange {
        return repository.getExchangeValue(currency, date)
    }
}