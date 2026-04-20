/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.calculadora.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.calculadora.R
import com.example.calculadora.presentation.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
         Calculadora()
        }
    }
}
@Composable
fun Calculadora() {
    var caja by remember{ mutableStateOf("0") }
    var resultado by remember { mutableStateOf(0.0) }
    var operador by remember { mutableStateOf("") }
    var dato1 by remember { mutableStateOf(0.0) }

    fun PresionarBoton(button: String) {
        when (button) {
            in "0".."9" -> {
                if (caja == "0") caja = button else caja += button
            }
            in listOf("/", "*", "-", "+") -> {
                operador = button
                dato1 = caja.toDouble()
                caja = "0"
            }
            "=" -> {
                resultado = when (operador) {
                    "/" -> dato1 / caja.toDouble()
                    "*" -> dato1 * caja.toDouble()
                    "-" -> dato1 - caja.toDouble()
                    "+" -> dato1 + caja.toDouble()
                    else -> 0.0
                }
                caja = resultado.toString()
            }
            "C" -> {
                resultado = 0.0
                caja = "0"
            }
            "+/-" -> {
                resultado = -1.0 * caja.toDouble()
                caja = resultado.toString()
            }
            "x2" -> {
                resultado = caja.toDouble() * caja.toDouble()
                caja = resultado.toString()
            }
            "√" -> {
                resultado = kotlin.math.sqrt(caja.toDouble())
                caja = resultado.toString()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calculadora")
        LazyColumn {
            item {
                Text(text = caja, style = MaterialTheme.typography.display3)
            }
            item {
                Renglones("7", "8", "9", "/", "C") { PresionarBoton(it) }
            }
            item {
                Renglones("4", "5", "6", "*", "+/-") { PresionarBoton(it) }
            }
            item {
                Renglones("1", "2", "3", "-", "x2") { PresionarBoton(it) }
            }
            item {
                Renglones("0", "=", "+", "√") { PresionarBoton(it) }
            }
        }
    }
}

@Composable
fun Renglones(vararg buttons: String, onClick: (String) -> Unit) {
    Row {
        buttons.forEach { button ->
            Button(
                modifier = Modifier
                    .padding(1.dp)
                    .size(width = 30.dp, height = 30.dp),
                onClick = { onClick(button) }
            ) {
                Text(text = button)
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    Calculadora()
}