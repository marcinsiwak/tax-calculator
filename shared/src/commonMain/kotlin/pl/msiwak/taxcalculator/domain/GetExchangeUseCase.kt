package pl.msiwak.taxcalculator.domain

import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.ExchangeRatesSeries
import pl.msiwak.taxcalculator.repository.ExchangesRepository

class GetExchangeUseCase(private val repository: ExchangesRepository) {

    suspend fun invoke(currency: Currency, date: LocalDate): ExchangeRatesSeries {
        return repository.getExchangeValue(currency, date)
    }
}