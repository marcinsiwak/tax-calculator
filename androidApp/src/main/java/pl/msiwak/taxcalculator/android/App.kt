package pl.msiwak.taxcalculator.android

import android.app.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pl.msiwak.taxcalculator.android.calculator.CalculatorViewModel
import pl.msiwak.taxcalculator.api.ExchangesApi
import pl.msiwak.taxcalculator.api.ExchangesApiImpl
import pl.msiwak.taxcalculator.domain.GetExchangeUseCase
import pl.msiwak.taxcalculator.repository.ExchangesRepository
import pl.msiwak.taxcalculator.repository.ExchangesRepositoryImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(viewModelsModule, appModule, repoModule, apiModule, useCaseModule)
        }

    }
}


val viewModelsModule = module {
    viewModel { CalculatorViewModel(get()) }

}

val appModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }
}

val repoModule = module {
    factory<ExchangesRepository> { ExchangesRepositoryImpl(get()) }
}

val apiModule = module {
    factory<ExchangesApi> { ExchangesApiImpl(get()) }
}

val useCaseModule = module {
    factory { GetExchangeUseCase(get()) }
}