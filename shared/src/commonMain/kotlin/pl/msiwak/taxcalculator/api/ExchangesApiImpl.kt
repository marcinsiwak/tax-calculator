package pl.msiwak.taxcalculator.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.Exchange

class ExchangesApiImpl(private val client: HttpClient) : ExchangesApi {

    override suspend fun getExchangeValue(currency: Currency, date: LocalDate): Exchange {
        return client.get("https://exchange-rates.abstractapi.com/v1/historical/?api_key=dccdf88c98524e7d9cce02334e81585a&base=${currency.name}&target=PLN&date=$date")
            .body()
    }
}