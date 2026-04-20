/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.lazy.presentation




import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Text


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}


@Composable
fun WearApp() {
    // Tema general de la aplicación para Wear OS (puedes definir un tema personalizado aquí)
    CustomTheme {
        // Estado de la lista lazy
        val listState = rememberLazyListState()

        // Modificadores comunes para los componibles de Wear
        val contentModifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        val iconModifier = Modifier.size(24.dp).wrapContentSize(align = Alignment.Center)


        // Scaffold de la aplicación para Wear
        ScreenScaffold(
            listState = listState,
            contentModifier = contentModifier,
            iconModifier = iconModifier
        )
    }
}

@Composable
fun ScreenScaffold(
    listState: LazyListState,
    contentModifier: Modifier,
    iconModifier: Modifier
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 32.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.Center,
        state = listState
    ) {
        // Sección de inicio (solo texto)
        item {
            StartOnlyTextComposables()
        }

        // Ejemplos simples de composables
        item {
            ButtonExample(
                modifier = contentModifier,
                iconModifier = iconModifier
            )
        }
        item {
            TextExample(
                modifier = contentModifier
            )
        }
        item {
            CardExample(
                modifier = contentModifier,
                iconModifier = iconModifier
            )
        }

        // Ejemplos únicos para Wear OS
        item {
            ChipExample(
                modifier = contentModifier,
                iconModifier = iconModifier
            )
        }
        item {
            ToggleChipExample(
                modifier = contentModifier
            )
        }
    }
}

// Funciones @Composable para ejemplos

@Composable
fun ButtonExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    // Implementación del botón aquí
    Text(
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Button Example"
    )
}

@Composable
fun TextExample(modifier: Modifier = Modifier) {
    // Implementación del texto aquí
    Text(
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Text Example"
    )
}

@Composable
fun CardExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    // Implementación de la tarjeta aquí
    Text(
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Card Example"
    )
}

@Composable
fun ChipExample(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
) {
    // Implementación del chip aquí
    Text(
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Chip Example"
    )
}

@Composable
fun ToggleChipExample(modifier: Modifier = Modifier) {
    // Implementación del toggle chip aquí
    Text(
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Toggle Chip Example"
    )
}

// Función de inicio solo para texto (preliminar)
@Composable
fun StartOnlyTextComposables() {
    Text(
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.Center,
        color = Color.Blue, // Color personalizado
        text = "Hello World Starter",
    )
}

@Composable
fun CustomTheme(content: @Composable () -> Unit) {
    // Puedes definir un tema personalizado aquí si lo deseas
    content()
}
/* Previsualizaciones de los composables */

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}