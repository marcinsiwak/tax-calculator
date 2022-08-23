package pl.msiwak.taxcalculator.android.utlis.extensions

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithHandler(block: suspend CoroutineScope.() -> Unit){
    launch(errorHandler, block = block)
}

private val errorHandler = CoroutineExceptionHandler { _, e ->
    Log.e("Error", "$e")
}