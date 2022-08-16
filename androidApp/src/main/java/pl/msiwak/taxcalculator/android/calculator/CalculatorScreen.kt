package pl.msiwak.taxcalculator.android.calculator

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.getViewModel

@Composable
fun CalculatorScreen() {
    val viewModel = getViewModel<CalculatorViewModel>()
    val state = viewModel.viewState.collectAsState()
    Text(state.value.currencyValue)

}

@Composable
fun InputField() {
//    TextField(value = , onValueChange = )
}