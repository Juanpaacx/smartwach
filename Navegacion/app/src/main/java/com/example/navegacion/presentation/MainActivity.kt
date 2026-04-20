/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.navegacion.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.navegacion.R
import com.example.navegacion.presentation.theme.NavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
         Navegacion()
        }
    }
}
@Composable
fun PaginaUno(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("RutaDos") }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_10k_24),
                contentDescription = "Siguiente"
            )
        }
        Text(text = "Uno")
    }
}

@Composable
fun PaginaDos(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("RutaTres") }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_10k_24),
                contentDescription = "Anterior"
            )
        }
        Text(text = "Dos")
    }
}

@Composable
fun PaginaCuatro(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("RutaCinco") }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_10k_24),
                contentDescription = "Anterior"
            )
        }
        Text(text = "Cuatro")
    }
}

@Composable
fun PaginaCinco(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_10k_24),
                contentDescription = "Anterior"
            )
        }
        Text(text = "cinco")
    }
}

@Composable
fun PaginaTres(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("RutaCuatro") }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_10k_24),
                contentDescription = "Anterior"
            )
        }
        Text(text = "Tres")
    }
}

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "RutaUno") {
        composable(route = "RutaUno") { PaginaUno(navController) }
        composable(route = "RutaDos") { PaginaDos(navController) }
        composable(route = "RutaTres") { PaginaTres(navController) }
        composable(route = "RutaCuatro") { PaginaCuatro(navController) }
        composable(route = "RutaCinco") { PaginaCinco(navController) }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
  Navegacion()
}