/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package drawable.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.producto.R
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape


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
        Imagene("Arie Camacho y los Plebes del Rancho",R.drawable.ariel),
        Imagene("Xartel De Santa",R.drawable.cartel),
        Imagene("Rosalia", R.drawable.rosalia),
        Imagene("Skrillex", R.drawable.skrillex),
        Imagene("Akon", R.drawable.akon),
        Imagene("Don Omar", R.drawable.donomar),
        Imagene("Bad Bunny", R.drawable.badbunny),
        Imagene("Plan B", R.drawable.planb)
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
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
  ListaImagenes()
}