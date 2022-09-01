package pl.msiwak.taxcalculator.data

enum class Currency {
    EUR, USD, PLN;

    fun getLowCaseName(): String = name.lowercase()
}