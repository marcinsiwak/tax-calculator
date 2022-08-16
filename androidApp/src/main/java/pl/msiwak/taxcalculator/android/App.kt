package pl.msiwak.taxcalculator.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import pl.msiwak.taxcalculator.android.calculator.CalculatorViewModel

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

    }
}

val appModule = module {
    viewModel { CalculatorViewModel() }

}