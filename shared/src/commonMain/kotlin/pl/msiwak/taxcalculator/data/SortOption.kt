package pl.msiwak.taxcalculator.data

sealed class SortOption {
    class Amount(val isDescending: Boolean = false): SortOption()
    class Rate(val isDescending: Boolean = false): SortOption()
    class ExchangedValue(val isDescending: Boolean = false): SortOption()
    class Type(val isBuy: Boolean = false): SortOption()
}