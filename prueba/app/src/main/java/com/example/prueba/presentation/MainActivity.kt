/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.prueba.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.prueba.R
import com.example.prueba.presentation.theme.PruebaTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.painter.Painter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            ListaImagenes()
        }
    }
}

data class Imagene( val nombre: String, val imagen: Int)
@Composable

fun ListaImagenes() {
    // Definir un arreglo de imágenes con sus nombres e imágenes
    val imagenes = listOf(
        Imagene("Avión",R.drawable.baseline_flight_24),
        Imagene("Transporte",R.drawable.baseline_commute_24),
        Imagene("Comida", R.drawable.baseline_fastfood_24),
        Imagene("Huella", R.drawable.baseline_fingerprint_24),
        Imagene("Calendario", R.drawable.baseline_calendar_month_24),
        Imagene("Torre de señal", R.drawable.baseline_cell_tower_24),
        Imagene("Pesa", R.drawable.baseline_fitness_center_24),
        Imagene("10 Mil", R.drawable.baseline_10k_24)
    )

    // Columna para organizar los elementos verticalmente con scroll
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()), // Scroll vertical
        horizontalAlignment = Alignment.CenterHorizontally // Centrar elementos horizontalmente
    ) {
        for (imagen in imagenes) {
            Text(
                text = imagen.nombre,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar el texto
            )
            Image(
                painter = painterResource(id = imagen.imagen),
                contentDescription = imagen.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally) // Centrar la imagen
            )
        }
    }
}

/*
@Composable
fun WearApp(greetingName: String) {
    PruebaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}
*/
@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ListaImagenes()
}