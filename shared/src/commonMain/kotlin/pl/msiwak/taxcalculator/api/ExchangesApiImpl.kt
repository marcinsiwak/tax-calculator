package pl.msiwak.taxcalculator.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import kotlinx.datetime.LocalDate
import pl.msiwak.taxcalculator.data.Currency
import pl.msiwak.taxcalculator.data.ExchangeRatesSeries

class ExchangesApiImpl(private val client: HttpClient) : ExchangesApi {

    override suspend fun getExchangeValue(currency: Currency, date: LocalDate): ExchangeRatesSeries {
        return client.get("http://api.nbp.pl/api/exchangerates/rates/a/${currency.getLowCaseName()}/$date/")
        {
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.ContentType, "application/json")
            }
        }
            .body()
    }
}