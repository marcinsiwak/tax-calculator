package pl.msiwak.taxcalculator.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import pl.msiwak.taxcalculator.data.Exchange
import pl.msiwak.taxcalculator.data.ExchangeRates

class ExchangesApiImpl(private val client: HttpClient) : ExchangesApi {

    override suspend fun getExchangeValue(): Exchange {
        val response: Exchange = client.get("https://exchange-rates.abstractapi.com/v1/live/?api_key=dccdf88c98524e7d9cce02334e81585a&base=PLN&target=EUR").body()
        return response
    }
}