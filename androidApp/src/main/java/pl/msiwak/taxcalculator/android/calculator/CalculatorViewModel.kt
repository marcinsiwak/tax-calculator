package pl.msiwak.taxcalculator.android.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import pl.msiwak.taxcalculator.presentation.CalculatorViewState

class CalculatorViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(CalculatorViewState())
    val viewState: StateFlow<CalculatorViewState>
        get() = _viewState
}