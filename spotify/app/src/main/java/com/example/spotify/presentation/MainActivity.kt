/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.spotify.presentation

//import androidx.wear.protolayout.material.ButtonDefaults
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Text
import com.example.spotify.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            MyScreen()
        }
    }
}
data class Imagene(val nombre: String, val imagen: Int)
@Composable
fun ListaImagenes() {

    val imagenes = listOf(
        Imagene("Arie Camacho", R.drawable.ariel),
        Imagene("Cartel De Santa", R.drawable.cartel),
        Imagene("Rosalia", R.drawable.rosalia),
        Imagene("Skrillex", R.drawable.skrillex),
        Imagene("Akon", R.drawable.akon),
        Imagene("Don Omar", R.drawable.donomar),
        Imagene("Bad Bunny", R.drawable.badbunny),
        Imagene("Plan B", R.drawable.planb)
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = CenterVertically
    ) {
        for (imagen in imagenes) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = imagen.imagen),
                    contentDescription = imagen.nombre,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = imagen.nombre,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun Etiqueta2() {
    Text(
        text = "Home",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun Etiqueta1() {
    Text(
        text = "Mejores artistas",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun Etiqueta3() {
    Text(
        text = " Recientementes",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
             .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))

    )
}
@Composable
fun ButtonsRow() {
    Row {
        CircularButton(text = "J", onClick = {})
        Spacer(modifier = Modifier.width(0.dp))
        RoundedButton(text = "All", onClick = {})
        Spacer(modifier = Modifier.width(0.dp))
        RoundedButton(text = "Music", onClick = {})
        Spacer(modifier = Modifier.width(0.dp))
        RoundedButton(text = "Podcast", onClick = {})
    }
}
@Composable
fun CircularButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(contentColor = Color.Green),
        modifier = Modifier
            .size(66.dp)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp

            )
        )
    }
}

@Composable
fun RoundedButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(90.dp)
            .height(40.dp)
            .padding(6.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        )
    }
}


data class Imagene3(val nombre: String, val imagen: Int)
@Composable
fun ListaImagenes3() {

    val imagenes3 = listOf(
        Imagene3("Arie Camacho", R.drawable.ariel),
        Imagene3("Cartel De Santa", R.drawable.cartel),
        Imagene3("Rosalia", R.drawable.rosalia),
        Imagene3("Skrillex", R.drawable.skrillex),
        Imagene3("Akon", R.drawable.akon),
        Imagene3("Don Omar", R.drawable.donomar),
        Imagene3("Bad Bunny", R.drawable.badbunny),
        Imagene3("Plan B", R.drawable.planb)
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = CenterVertically
    ) {
        for (imagen3 in imagenes3) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = imagen3.imagen),
                    contentDescription = imagen3.nombre,
                    modifier = Modifier
                        .size(152.dp)
                        .clip(RoundedCornerShape(75.dp))
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = imagen3.nombre,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
data class Imagene2(val nombre: String, val imagen: Int)
@Composable
fun ListaImagenes2() {

    val imagenes2 = listOf(
        Imagene2("Tus Me gusta", R.drawable.likes),
        Imagene2("Cartel De Santa", R.drawable.cartel),
        Imagene2("Rosalia", R.drawable.rosalia),
        Imagene2("Skrillex", R.drawable.skrillex),
        Imagene2("Akon", R.drawable.akon),
        Imagene2("Don Omar", R.drawable.donomar),
        Imagene2("Bad Bunny", R.drawable.badbunny),
        Imagene2("Plan B", R.drawable.planb)
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = CenterVertically
    ) {
        for (imagen2 in imagenes2) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = imagen2.imagen),
                    contentDescription = imagen2.nombre,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = imagen2.nombre,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Composable
fun MyScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(all = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Envolver la Column con verticalScroll
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Etiqueta2()
            Spacer(modifier = Modifier.height(3.dp))
            ButtonsRow()
            Spacer(modifier = Modifier.height(3.dp))
            ListaImagenes()
            Spacer(modifier = Modifier.height(3.dp))
            Etiqueta1()
            ListaImagenes2()
            Spacer(modifier = Modifier.height(3.dp))
            Etiqueta3()
            ListaImagenes3()
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MyScreen()

}