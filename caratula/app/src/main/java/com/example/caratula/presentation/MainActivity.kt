/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.caratula.presentation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.caratula.R
import kotlinx.coroutines.delay

import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults

import androidx.wear.compose.material.Icon
import java.time.LocalTime
import java.time.format.DateTimeFormatter


import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Scaffold

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType



import androidx.compose.foundation.gestures.detectDragGestures

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*


data class Song(val title: String, val artist: String, val resId: Int, val imageResId: Int)
data class Genre(val name: String, val songs: List<Song>)

// Global variables for MediaPlayer
private var mediaPlayer: MediaPlayer? = null
private var currentSongResId: Int? = null
private var isPlaying: Boolean by mutableStateOf(false)

// List of genres
val genres = listOf(
    Genre(
        name = "Liked songs",
        songs = listOf(
            Song(title = "You are not alone", artist = "Michael Jackson", resId = R.raw.segundo, imageResId = R.drawable.descargam),
            Song(title = "Temperature", artist = "Sean Paul", resId = R.raw.primero, imageResId = R.drawable.seal)
        )
    )
)

// Main Activity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            AppNavigation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}


/*
@Composable
fun AppNavigation2() {
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = "RutaWearApp"
    ) {
        composable("RutaAppContenedor3") {
            Contenedor2()
        }
        composable("RutaWearApp") {
            WearApp(navController = navController)
        }
    }
}
*/

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "RutaHome") {
        composable(route = "RutaHome") {
            MainScreen(navController)
        }
        composable(route = "RutaApps") {
            WearApp(navController)
        }
        composable(route = "RutaCalculadora") {
            ChipCalculadora(navController)
        }
        composable(route = "RutaAppCalculadora") {
            Calculadora(navController)
        }
        composable(route = "RutaReproductor") {
            ChipReproductor(navController)
        }
        composable(route = "RutaAppContenedor2") {
            Contenedor2(navController)
        }
        composable(route = "RutaAppReproductor") {
            MusicPlayerScreen(navController)
        }
        composable(route = "RutaAppContenedor3") {
            Contenedor3(navController)
        }
        composable(route = "RutaGaleriaa") {
            WearAppp(navController)
        }
        composable(route = "RutaCronometro") {
            Page4(navController)
        }
        composable(route = "RutaReproductorMusica") {
            MusicPlayerScreenn(navController)
        }
        composable(route = "RutaRedordatorio") {
        AppNavigation2()
        }
        composable(route = "RutaCaloriesCalculator") {
            // Render your CaloriesCalculatorView here
            CaloriesCalculatorView(callbackNavigation = { route ->
                navController.navigate(route)
            })
        }
        composable(route = "RutaAcerca") {
           SoftwareSpecsScreen(navController)
        }

        composable(route = "RutaPizarra") {
            CanvasDrawingBoard(navController)
        }
        composable(route = "Rutaf") {
            Contenedorf(navController)
        }
    }
       /* composable("main_reminder") {
            val reminders = remember { mutableStateOf(listOf<Reminder>()) }
            MainReminderScreen(navController = navController, reminders = reminders.value) { newReminder ->
                reminders.value = reminders.value + newReminder
            }
        }*/

        /*composable("add_reminder") {
            val reminders = remember { mutableStateOf(listOf<Reminder>()) }
            AddReminderScreen(navController = navController) { newReminder ->
                reminders.value = reminders.value + newReminder
            }
        }*/

    }


@Composable
fun MainScreen(navController: NavHostController) {
    var currentTime by remember { mutableStateOf("") }
    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val localTime = LocalTime.now()
            currentTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            hours.value = localTime.hour.toString()
            minutes.value = localTime.minute.toString()
            delay(1000) // Actualiza la hora cada segundo
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        // Fondo de pantalla, si tienes un recurso drawable para esto
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna central para la hora y fecha
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize() // Asegurarse de que la columna ocupe todo el espacio
        ) {
            Text(
                text = "TUE 18",
                color = Color(192, 192, 192), // Color LightGray en RGB
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            // Números de la hora y los minutos
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hours.value,
                    color = Color(63, 168, 182, 255), // Color Gray en RGB
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = ":",
                    color = Color(115, 194, 186, 255), // Color Cyan en RGB
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = minutes.value,
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Espaciador flexible para empujar el botón hacia abajo

            // Botón para navegar a la pantalla de detalle
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("RutaApps") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_apps_24),
                        contentDescription = "Siguiente"
                    )
                },
                text = { Text(text = "Iniciar", color = Color.White) }, // Cambia el color del texto aquí
                shape = RoundedCornerShape(12.dp), // Bordes redondeados
                containerColor = Color.DarkGray, // Color del botón
                contentColor = Color.White, // Color del contenido (icono y texto por defecto)
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .background(Color.Transparent)
                    .width(120.dp)
                    .height(36.dp)
            )

            Spacer(modifier = Modifier.height(1.dp)) // Añadir un espacio entre el botón y el Row

            // Row para mostrar los pasos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Alinear el Row horizontalmente en el centro
                    .padding(bottom = 16.dp) // Añadir un padding inferior
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jogging),
                    contentDescription = "Steps Icon",
                    modifier = Modifier.size(24.dp)

                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "7,094",
                    color = Color(246, 247, 248, 255), // Color Cyan en RGB
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}
/*
@Composable
fun DetailScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text("Pantalla de Detalle", color = Color.White)
        Button(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(
                    id =
                    R.drawable.baseline_arrow_back_24
                ),
                contentDescription = "Anterior"
            )
        }
        Text(text = "cinco")

    }
}

*/


@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun ChipExample() {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { /* ... */ },
        label = {
            Text(
                text = "Item",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = android.R.drawable.btn_star_big_on),
                contentDescription = "Star",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}
// calculadora

@Composable
fun ChipCalculadora(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaAppCalculadora") },
        label = {
            Text(
                text = "Calculadora",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calculate_24),
                contentDescription = "Calculadora",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}
//ChipReprocutor

@Composable
fun ChipReproductor(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaAppReproductor") },
        label = {
            Text(
                text = "Musica",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "Calculadora",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}
//Caratula1

@Composable
fun ChipCaratula2(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaAppContenedor2") },
        label = {
            Text(
                text = "Caratula2",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "Calculadora",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}


@Composable
fun ChipCaratula3(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaAppContenedor3") },
        label = {
            Text(
                text = "Caratula3",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "Calculadora",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}

@Composable
fun ChipCaratulaf(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("Rutaf") },
        label = {
            Text(
                text = "caratula",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.run),
                contentDescription = "Calculadora",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}



@Composable
fun ChipGaleria(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaGaleriaa") },
        label = {
            Text(
                text = "Galeria",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = "Galeria",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}


@Composable
fun ChipReproductorM(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaReproductorMusica") },
        label = {
            Text(
                text = "ReproductorM",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_library_music_24),
                contentDescription = "Reproductor",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}

@Composable
fun ChipRecordatorio(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaRedordatorio") },
        label = {
            Text(
                text = "Recordatorio",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_note_add_24),
                contentDescription = "Recordatorio",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}

@Composable
fun Chippizarra(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaPizarra") },
        label = {
            Text(
                text = "Pizarra",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_note_add_24),
                contentDescription = "Recordatorio",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}


@Composable
fun ChipCalorias(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaCaloriesCalculator") },
        label = {
            Text(
                text = "Calorias",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_local_fire_department_24),
                contentDescription = "Recordatorio",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}



@Composable
fun ChipAcerca(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaAcerca") },
        label = {
            Text(
                text = "Acerca de",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_info_outline_24),
                contentDescription = "Acerca de",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}


@Composable
fun ChipCronometro(navController: NavHostController) {
    Chip(
        modifier = Modifier
            .width(200.dp)
            .padding(top = 10.dp),
        onClick = { navController.navigate("RutaCronometro") },
        label = {
            Text(
                text = "Cronometro",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_timer_24),
                contentDescription = "Galeria",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
    )
}

@Composable
fun ButtonExample(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier


) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(width = 90.dp, height = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "triggers phone action",
                modifier = iconModifier,
            )
        }
    }
}


@Composable
fun TextExample() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "Aplicasiones",
    )
}
@Composable
fun WearApp(navController: NavHostController) {
    val listState = rememberScalingLazyListState()

    AppScaffold {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 10.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 40.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                TextExample()
            }
            item {
                Spacer(modifier = Modifier.height(13.dp))
            }
            item {
                ButtonExample(navController)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                ChipCalculadora(navController)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
               ChipReproductor(navController)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                ChipCaratula2(navController)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                ChipCaratula3(navController)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                ChipGaleria(navController)
            }
            item {
                ChipCronometro(navController)
            }
            item {
                ChipReproductorM(navController)
            }
            item {
                ChipRecordatorio(navController)
            }
            item {
                ChipCalorias(navController)
            }
            item {
                ChipAcerca(navController)
            }
            item {
                Chippizarra(navController)
            }
            item {
                ChipCaratulaf(navController)
            }
        }
    }
}

//apps
//Recordatorio
/*
@Composable
fun ContrRecordatorio(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main_reminder") {
        composable("main_reminder") {
            MainReminderScreen(navController, reminders) { newReminder ->
                reminders = reminders + newReminder
                if (newReminder.time != null) {
                    scheduleReminder(newReminder.text, newReminder.time, navController.context)
                }
            }
        }
        composable("add_reminder") {
            AddReminderScreen(navController) { newReminder ->
                reminders = reminders + newReminder
                if (newReminder.time != null) {
                    scheduleReminder(newReminder.text, newReminder.time, navController.context)
                }
            }
        }
        // Otras pantallas componibles...
    }
}
@Composable
fun MainReminderScreen(navController: NavHostController, reminders: List<Reminder>, addReminder: (Reminder) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reminders",
            modifier = Modifier,
            fontSize = 15.sp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(reminders) { reminder ->
                ReminderItem(reminder = reminder)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("add_reminder") },
            modifier = Modifier.width(100.dp).height(30.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.width(50.dp),
            colors = androidx.compose.ui.graphics.ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            androidx.compose.material.icons(
                Icons.Filled.ArrowBack,
                contentDescription = "Go back",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ReminderItem(reminder: Reminder) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text(
            text = "${reminder.text} - ${if (reminder.time != null) "in ${reminder.time} seconds" else "No time set"}",
            modifier = Modifier
                .width(150.dp) // Set a fixed width
                .padding(vertical = 4.dp)
                .align(Alignment.CenterHorizontally),
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AddReminderScreen(navController: NavHostController, addReminder: (Reminder) -> Unit) {
    var reminderText by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add a Reminder", fontSize = 15.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            label = { Text("Reminder Text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("Reminder Time (in seconds, optional)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val timeInSeconds = reminderTime.toLongOrNull()
                addReminder(Reminder(reminderText, timeInSeconds))
                navController.popBackStack()
            },
            modifier = Modifier.width(100.dp).height(40.dp)
        ) {
            Text("Save")
        }
    }
}

data class Reminder(val text: String, val time: Long?)

fun scheduleReminder(reminderText: String, delayInSeconds: Long, context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
        .setInputData(workDataOf("reminderText" to reminderText, "reminderId" to reminderText.hashCode()))
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}





*/
@Composable
fun AppNavigation2() {
    val navController = rememberNavController()
    val reminders = remember { mutableStateListOf<Reminder>() }

    NavHost(navController = navController, startDestination = "main_reminder_screen") {
        composable("main_reminder_screen") {
            MainReminderScreen(navController, reminders)
        }
        composable("add_reminder") {
            AddReminderScreen(navController, reminders)
        }
    }
}

@Composable
fun MainReminderScreen(navController: NavHostController, reminders: List<Reminder>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Reminders", fontSize = 15.sp)
        reminders.forEach { reminder ->
            Text(
                text = "${reminder.text} - ${if (reminder.time != null) "in ${reminder.time} seconds" else "No time set"}",
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 4.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { navController.navigate("add_reminder") },
            modifier = Modifier
                .width(100.dp)
                .height(30.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(60.dp))

    }
}

@Composable
fun AddReminderScreen(navController: NavHostController, reminders: MutableList<Reminder>) {
    var reminderText by remember { mutableStateOf("") }
    var reminderTime by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add a Reminder", fontSize = 15.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            label = { Text("Reminder Text") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = reminderTime,
            onValueChange = { reminderTime = it },
            label = { Text("Reminder Time (in seconds, optional)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val timeInSeconds = reminderTime.toLongOrNull()
                val reminder = Reminder(reminderText, timeInSeconds)
                reminders.add(reminder)
                timeInSeconds?.let {
                    scheduleReminder(reminderText, it, context)
                }
                navController.popBackStack()
            },
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
        ) {
            Text("Save")
        }
    }
}

fun scheduleReminder(reminderText: String, delayInSeconds: Long, context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
        .setInputData(workDataOf("reminderText" to reminderText, "reminderId" to reminderText.hashCode()))
        .build()
    WorkManager.getInstance(context).enqueue(workRequest)
}

class ReminderWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val reminderText = inputData.getString("reminderText") ?: return Result.failure()
        val reminderId = inputData.getInt("reminderId", 0)
        showNotification(reminderText, reminderId)
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(reminderText: String, reminderId: Int) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "reminder_channel"
        val channel = NotificationChannel(channelId, "Reminder Notifications", NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Channel for reminder notifications"
        }
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Reminder")
            .setContentText(reminderText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(applicationContext).notify(reminderId, notification)
    }
}

data class Reminder(val text: String, val time: Long?)


// Music Player Screen
@Composable
fun MusicPlayerScreenn(navController: NavController) {
    val genre = remember { genres.first() }
    SongScreen(
        genre = genre,
        playSong = { resId -> playOrPauseSong(navController.context, resId) }
    )
}

// Function to play or pause song
private fun playOrPauseSong(context: Context, resId: Int) {
    if (mediaPlayer == null || currentSongResId != resId) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resId).apply { start() }
        currentSongResId = resId
        isPlaying = true
    } else {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPlaying = false
            } else {
                it.start()
                isPlaying = true
            }
        }
    }
}

// Song Screen Composable
@Composable
fun SongScreen(
    genre: Genre,
    playSong: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = genre.name,
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            genre.songs.forEach { song ->
                SongItem(song = song, playSong = playSong)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

// Song Item Composable
@Composable
fun SongItem(song: Song, playSong: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = song.imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = song.title,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = song.artist,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { playSong(song.resId) },
            modifier = Modifier
                .width(120.dp)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD709))
        ) {
            Text(if (isPlaying) "Pause" else "Play", color = Color.Black)
        }
    }
}


//

enum class ActivityLevel(val multiplier:Float) {
    SEDENTARY(1.2f),
    LIGHTLY_ACTIVE(1.375f),
    MODERATELY_ACTIVE(1.55f),
    VERY_ACTIVE(1.725f),
    EXTRA_ACTIVE(1.9f)

}

enum class Gender {
    MALE, FEMALE;

    override fun toString(): String {
        return when (this) {
            MALE -> "Hombre"
            FEMALE -> "Mujer"
        }
    }
}

enum class Goal {
    MAINTAIN {
        override fun toString(): String {
            return "Mantener peso"
        }
    },
    LOSE_WEIGHT {
        override fun toString(): String {
            return "Perder peso"
        }
    },
    GAIN_WEIGHT {
        override fun toString(): String {
            return "Ganar peso"
        }
    }
}

data class UserInput(
    val age:Int,
    val weight:Float,
    val height:Int,
    val gender: Gender,
    val activityLevel:ActivityLevel,
    val goal:Goal,

    )

fun calculateCalories(userInput: UserInput):Int{
    val weightInKg = userInput.weight
    val heightInCm = userInput.height.toFloat()
    val age = userInput.age

    val bmr = when (userInput.gender) {
        Gender.MALE -> (10 * weightInKg + 6.25 * heightInCm - 5 * age + 5).toInt()
        Gender.FEMALE -> (10 * weightInKg + 6.25 * heightInCm - 5 * age - 161).toInt()
    }

    val activityMultiplier = userInput.activityLevel.multiplier
    var calories = (bmr * activityMultiplier).toInt()

    when (userInput.goal) {
        Goal.LOSE_WEIGHT -> calories -= 500 // Reducir 500 calorías por día para perder peso
        Goal.GAIN_WEIGHT -> calories += 500 // Añadir 500 calorías por día para ganar peso
        Goal.MAINTAIN -> Unit // Mantener el mismo consumo de calorías
    }

    return calories
}

@Composable
fun CaloriesCalculatorView(callbackNavigation:(route:String)->Unit){
    var weight by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableIntStateOf(0) }
    var age by remember { mutableIntStateOf(0) }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var activityLevel by remember { mutableStateOf(ActivityLevel.SEDENTARY) }
    var goal by remember { mutableStateOf(Goal.MAINTAIN) }

    var totalCalories by remember {
        mutableIntStateOf(0)
    }

    var genderOptions = listOf(Gender.MALE, Gender.FEMALE)
    var activityLevelOptions = listOf(ActivityLevel.SEDENTARY,ActivityLevel.LIGHTLY_ACTIVE,ActivityLevel.MODERATELY_ACTIVE,ActivityLevel.VERY_ACTIVE,ActivityLevel.EXTRA_ACTIVE)
    var goalOptions = listOf(Goal.LOSE_WEIGHT,Goal.MAINTAIN,Goal.GAIN_WEIGHT)
    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            IconButton(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                onClick = { callbackNavigation("menu") }

            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Regresar",
                    tint = Color.Yellow
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "Tus datos",
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = weight.toString(),
            onValueChange = {weight = it.toFloatOrNull() ?: 0f},
            label = {Text("Peso (Kg)")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        OutlinedTextField(
            value =height.toString() ,
            onValueChange = {height = it.toIntOrNull() ?: 0},
            label = {Text("Altura (cm)")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, color = Color.White),

                    )
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            genderOptions.forEach { ger ->
                Text(ger.toString())
                RadioButton(
                    selected = gender == ger,
                    onClick = {
                        gender = ger
                    },
                    enabled = true,

                    )
            }        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = age.toString(),
            onValueChange = {age = it.toIntOrNull() ?: 0},
            label = { Text(text = "Edad")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, Color.White),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
        ) {
            Text(text="Nivel de actividad", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            activityLevelOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = activityLevel == option,
                        onClick = {
                            activityLevel = option
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = option.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .border(
                    BorderStroke(1.dp, Color.White),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
        ) {
            Text(text="Objetivo", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            goalOptions.forEach { g ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = goal == g,
                        onClick = {
                            goal = g
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = g.toString())
                }
            }
        }

        if(totalCalories >0){
            Text(text="Total calorias: ${totalCalories}")
        }

        Spacer(Modifier.height(10.dp))
        MyButton(text = "Calcular calorias") {
            val userInput = UserInput(weight=weight,height=height, gender = gender, activityLevel = activityLevel, goal = goal, age=age)
            totalCalories = calculateCalories(userInput);
            //print(calories)
            //callbackNavigation("calories/150")
        }

    }
}


@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Ajusta el padding horizontal según tu diseño
        horizontalArrangement = Arrangement.Center // Centra horizontalmente el botón
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
        ) {
            Text(text,
                fontSize = 14.sp, // Tamaño del texto ajustado
                textAlign = TextAlign.Center)
        }
    }
}
//Cronometro


@Composable
fun Page4(navController: NavController) {
    var time by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L)
            time++

        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = String.format("%02d: %02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
                style = MaterialTheme.typography.display3,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { isRunning = true },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor =Color(255, 215, 9))
                ) {
                    Text("Start", color = Color.Black)
                }
                Button(
                    onClick = { isRunning = false },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor =Color(240, 230, 140))
                ) {
                    Text("Stop", color = Color.Black)
                }
                Button(
                    onClick = {
                        isRunning = false
                        time = 0L
                    },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                ) {
                    Text(text = "Reset", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                androidx.wear.compose.material.Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

//Calculadora

@Composable
fun Calculadora(navController: NavHostController) {
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

//REPRODUCTOR

@Composable
fun MusicPlayerScreen(navController: NavHostController) {
    Scaffold(
        timeText = { TimeText() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF6C4F9E), Color(0xFF0C0A0E))
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chef Table Radio",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Eli Kulp",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ControlButton(
                    onClick = { /* Previous track action */ },
                    iconId = R.drawable.baseline_skip_previous_24,
                    description = "Previous"
                )
                ControlButton(
                    onClick = { /* Play/Pause action */ },
                    iconId = R.drawable.baseline_pause_24,
                    description = "Play",
                    backgroundColor = Color.Transparent,
                    borderColor = Color.Magenta, // Aquí especificamos el color del borde
                    borderWidth = 2.dp
                )
                ControlButton(
                    onClick = { /* Next track action */ },
                    iconId = R.drawable.baseline_skip_next_24,
                    description = "Next"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ControlButton(
                    onClick = { /* Like action */ },
                    iconId = R.drawable.baseline_favorite_border_24,
                    description = "Like"
                )
                ControlButton(
                    onClick = { /* Volume action */ },
                    iconId = R.drawable.baseline_volume_up_24,
                    description = "Volume"
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            HorizontalPageIndicator(
                pageCount = 3,
                currentPage = 1 // Aquí puedes usar un estado para manejar la página actual
            )
        }
    }
}

@Composable
fun HorizontalPageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colors.onBackground,
    unselectedColor: Color = selectedColor.copy(alpha = 0.3f),
    indicatorSize: Dp = 10.dp,
    spacing: Dp = 4.dp,
    indicatorShape: Shape = CircleShape
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        for (i in 0 until pageCount) {
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .padding(horizontal = spacing / 2)
                    .background(
                        color = if (i == currentPage) selectedColor else unselectedColor,
                        shape = indicatorShape
                    )
            )
        }
    }
}

@Composable
fun ControlButton(
    onClick: () -> Unit,
    iconId: Int,
    description: String,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent, // Agregado aquí
    borderWidth: Dp = 1.dp // Agregado aquí
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier
            .size(48.dp)
            .border(borderWidth, borderColor, CircleShape), // Añadido el borde aquí
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = description,
            tint = Color.White
        )
    }
}
//Caratula1
data class Botones(val id: Int, val rotulo: String)


@Composable
fun ListaBotones() {
    val botonseleccionado = remember { mutableStateOf<Botones?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//baseline_directions_run_24
        Image(
            painter = painterResource(id = R.drawable.baseline_run_circle_24),
            contentDescription = "Running Icon",
            modifier = Modifier
                .padding(2.dp),
            colorFilter = ColorFilter.tint(Color.Yellow)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto "7:21 / mi"
        Text(
            text = "7:21 / mi",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )


        Text(
            text = "Running",
            color = Color.Yellow,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        botonseleccionado.value?.let { selected ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Botón seleccionado: ${selected.rotulo}")
        }
    }
}



@Composable
fun Contenedor2(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListaBotones()
    }
}

//Caratula2
data class Botones2(val id: Int, val rotulo: String)

@Composable
fun ListaBotones2() {
    val botonseleccionado = remember { mutableStateOf<Botones2?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Power Yoga",
            color = Color.Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { botonseleccionado.value = Botones2(id = 0, rotulo = "Start") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(72.dp), // Ajusta la altura del botón según sea necesario
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (botonseleccionado.value?.id == 0) Color.Gray else Color.Yellow
            )
        ) {
            Text(
                text = "Start",
                color = Color.Black, // Color del texto negro
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = 0.dp) // Mueve el texto hacia abajo
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Last session 45m",
            color = Color.White,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        botonseleccionado.value?.let { selected ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Botón seleccionado: ${selected.rotulo}")
        }
    }
}
@Composable
fun Contenedor3(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListaBotones2()
    }
}



@Composable
fun ControlButtonn(
    onClick: () -> Unit,
    iconId: Int,
    description: String,
    backgroundColor: Color = Color.Gray,
    iconTint: Color = Color.White,
    size: Int = 35 // Tamaño más pequeño
) {
    Surface(
        modifier = Modifier
            .size(size.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = backgroundColor,
        contentColor = iconTint
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = description,
            modifier = Modifier.size((size * 0.5).dp) // Tamaño del ícono más pequeño
        )
    }
}

data class Botonesf(val id: Int, val rotulo: String)

@Composable
fun ListaBotonesf() {
    val botonseleccionado = remember { mutableStateOf<Botonesf?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "1 run this week",
            color = Color.Yellow,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ControlButtonn(
                onClick = { /* Acción del botón 1 */ },
                iconId = R.drawable.baseline_accessibility_new_24,
                description = "Accessibility",
                backgroundColor = Color.Yellow,
                iconTint = Color.White
            )

            ControlButtonn(
                onClick = { /* Acción del botón 2 */ },
                iconId = R.drawable.run,
                description = "Accessibility",
                backgroundColor = Color.Yellow,
                iconTint = Color.White
            )

            ControlButtonn(
                onClick = { /* Acción del botón 3 */ },
                iconId = R.drawable.bicycle,
                description = "Accessibility",
                backgroundColor = Color.Yellow,
                iconTint = Color.White
            )
        }

        botonseleccionado.value?.let { selected ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Botón seleccionado: ${selected.rotulo}")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { botonseleccionado.value = Botonesf(id = 0, rotulo = "More") },
            modifier = Modifier

                .padding(8.dp)
                .height(22.dp) // Ajusta la altura del botón según sea necesario
                .width(50.dp), // Ajusta el ancho del botón según sea necesario
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray // Color gris para el botón
            )
        ) {
            Text(
                text = "More",
                color = Color.Black, // Ajusta el color del texto según sea necesario
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Contenedorf(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListaBotonesf()
    }
}
//Aqui empieza la galeria



@Composable
fun RoundedBlurImageButton(
    image: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.3f)) // Semi-transparent overlay
                .clip(RoundedCornerShape(2.dp)) // Round corners
        ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 0.5f // Apply blur effect
                    }
                    .background(Color.Black.copy(alpha = 0.5f))
            )
        }
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun WearAppp( navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "PaginaUno") {
        composable(route = "PaginaUno") {
            PaginaUno(navController)
        }
        composable(route = "All") {
            GaleriaMedios(navController)
        }
        composable(route = "camara") {
            GaleriaCamara(navController)
        }
        composable(route = "screenshot") {
            GaleriaScreenshot(navController)
        }
        composable(route = "Videos") {
            GaleriaVideos(navController)
        }/*
        composable("detalle/{photoFileName}") { backStackEntry ->
            val photoFileName = backStackEntry.arguments?.getString("photoFileName")
            ContainerTransformDemo(navController = navController, selectedFileName = photoFileName)
        }

    }
    */
    }
}
@Composable
fun PaginaUno(navController: NavController) {
    val listState: ScalingLazyListState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(4) { index ->
            val (imageResource, buttonText) = when (index) {
                0 -> painterResource(id = R.drawable.captura9) to "All"
                1 -> painterResource(id = R.drawable.camara1) to "Camera"
                2 -> painterResource(id = R.drawable.camara10) to "Screenshot"
                3 -> painterResource(id = R.drawable.captura6) to "Videos"
                else -> painterResource(id = R.drawable.camara1) to "Default"
            }
            RoundedBlurImageButton(
                image = imageResource,
                text = buttonText,
                onClick = {
                    when (index) {
                        0 -> navController.navigate("all")
                        1 -> navController.navigate("camara")
                        2 -> navController.navigate("screenshot")
                        3 -> navController.navigate("videos")
                    }
                }
            )
        }
    }
}


@Immutable
data class Photo(
    val model: Int,
    val fileName: String,
    val creationDate: String,
    val modificationDate: String,
    val fileType: String,
    val fileSize: String,
    val tags: List<String>
)

val randomSizedPhotos = listOf(
    Photo(
        model = R.drawable.camara1,
        fileName = "Camara1.jpg",
        creationDate = "2023-01-01",
        modificationDate = "2023-06-01",
        fileType = "JPG",
        fileSize = "2MB",
        tags = listOf("Camara", "All")
    ),//screenshot
    Photo(
        model = R.drawable.camara2,
        fileName = "Camara2.jpg",
        creationDate = "2023-02-01",
        modificationDate = "2023-06-02",
        fileType = "JPEG",
        fileSize = "3MB",
        tags = listOf("Camara", "All")

    ),
    Photo(
        model = R.drawable.camara3,  // Dejar en blanco
        fileName = "camara3.jpg",
        creationDate = "2023-01-01",
        modificationDate = "2023-06-01",
        fileType = "JPG",
        fileSize = "2MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model = R.drawable.camara4,  // Dejar en blanco
        fileName = "camara4.jpg",
        creationDate = "2023-02-01",
        modificationDate = "2023-06-02",
        fileType = "JPEG",
        fileSize = "3MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model = R.drawable.camara6,  // Dejar en blanco
        fileName = "camara6.jpg",
        creationDate = "2023-03-01",
        modificationDate = "2023-06-03",
        fileType = "PNG",
        fileSize = "4MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model = R.drawable.camara7,  // Dejar en blanco
        fileName = "camara7.jpg",
        creationDate = "2023-04-01",
        modificationDate = "2023-06-04",
        fileType = "GIF",
        fileSize = "1MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model = R.drawable.camara8,  // Dejar en blanco
        fileName = "camara8.jpg",
        creationDate = "2023-05-01",
        modificationDate = "2023-06-05",
        fileType = "JPEG",
        fileSize = "5MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model =R.drawable.camara9,  // Dejar en blanco
        fileName = "camara9.jpg",
        creationDate = "2023-06-01",
        modificationDate = "2023-06-06",
        fileType = "PNG",
        fileSize = "2.5MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model =R.drawable.camara10,  // Dejar en blanco
        fileName = "camara10.jpg",
        creationDate = "2023-07-01",
        modificationDate = "2023-06-07",
        fileType = "PNG",
        fileSize = "3.5MB",
        tags = listOf("Camara", "All")
    )
)
@Immutable
data class screenshot(
    val model: Int,
    val fileName: String,
    val creationDate: String,
    val modificationDate: String,
    val fileType: String,
    val fileSize: String,
    val tags: List<String>
)
val randomSizedscreenshot = listOf(
    screenshot(
        model = R.drawable.captura1,  // Dejar en blanco
        fileName = "screenshot1.png",
        creationDate = "2023-11-01",
        modificationDate = "2023-06-11",
        fileType = "PNG",
        fileSize = "2MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura2,  // Dejar en blanco
        fileName = "screenshot2.png",
        creationDate = "2023-11-02",
        modificationDate = "2023-06-12",
        fileType = "PNG",
        fileSize = "2.5MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura3,  // Dejar en blanco
        fileName = "screenshot3.png",
        creationDate = "2023-11-03",
        modificationDate = "2023-06-13",
        fileType = "PNG",
        fileSize = "3MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura4,  // Dejar en blanco
        fileName = "screenshot4.png",
        creationDate = "2023-11-04",
        modificationDate = "2023-06-14",
        fileType = "PNG",
        fileSize = "3.5MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura5,  // Dejar en blanco
        fileName = "screenshot5.png",
        creationDate = "2023-11-05",
        modificationDate = "2023-06-15",
        fileType = "PNG",
        fileSize = "4MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura6,  // Dejar en blanco
        fileName = "screenshot6.png",
        creationDate = "2023-11-06",
        modificationDate = "2023-06-16",
        fileType = "PNG",
        fileSize = "4.5MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura7,  // Dejar en blanco
        fileName = "screenshot7.png",
        creationDate = "2023-11-07",
        modificationDate = "2023-06-17",
        fileType = "PNG",
        fileSize = "5MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura8,  // Dejar en blanco
        fileName = "screenshot8.png",
        creationDate = "2023-11-08",
        modificationDate = "2023-06-18",
        fileType = "PNG",
        fileSize = "5.5MB",
        tags = listOf("screenshot", "All")
    ),
    screenshot(
        model = R.drawable.captura9,  // Dejar en blanco
        fileName = "screenshot9.png",
        creationDate = "2023-11-08",
        modificationDate = "2023-06-18",
        fileType = "PNG",
        fileSize = "5.5MB",
        tags = listOf("screenshot", "All")
    )
)
@Immutable
data class Video(
    val videoResId: Int,
    val fileName: String,
    val creationDate: String,
    val modificationDate: String,
    val fileType: String,
    val fileSize: String,
    val tags: List<String>
)
val videos = listOf(
    Video(
        videoResId = R.raw.video2,
        fileName = "video2.mp4",
        creationDate = "2023-04-01",
        modificationDate = "2023-07-01",
        fileType = "MP4",
        fileSize = "12MB",
        tags = listOf("Camara", "All")
    ),
    Video(
        videoResId = R.raw.video3,
        fileName = "video3.mp4",
        creationDate = "2023-05-01",
        modificationDate = "2023-08-01",
        fileType = "MP4",
        fileSize = "15MB",
        tags = listOf("Camara", "All")
    ),
    Video(
        videoResId = R.raw.video4,
        fileName = "video4.mp4",
        creationDate = "2023-06-01",
        modificationDate = "2023-09-01",
        fileType = "MP4",
        fileSize = "11MB",
        tags = listOf("Camara", "All")
    ),
    Video(
        videoResId = R.raw.video5,
        fileName = "video5.mp4",
        creationDate = "2023-07-01",
        modificationDate = "2023-10-01",
        fileType = "MP4",
        fileSize = "13MB",
        tags = listOf("Camara", "All")
    ),
    Video(
        videoResId = R.raw.video6,
        fileName = "video6.mp4",
        creationDate = "2023-08-01",
        modificationDate = "2023-11-01",
        fileType = "MP4",
        fileSize = "14MB",
        tags = listOf("Camara", "All")
    ),
    Video(
        videoResId = R.raw.video7,
        fileName = "video7.mp4",
        creationDate = "2023-09-01",
        modificationDate = "2023-12-01",
        fileType = "MP4",
        fileSize = "10MB",
        tags = listOf("Camara", "All")
    )
)

@Composable
fun ImageFullScreen(photo: Photo, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = photo.model),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun GaleriaCamara(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad de la imagen seleccionada
    val selectedPhoto = remember { mutableStateOf<Photo?>(null) }

    // Estado para controlar la lista de fotos mostradas
    val photos = remember { mutableStateOf(randomSizedPhotos) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = photos.value.chunked(2) // Agrupa las fotos en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rows[rowIndex].forEach { photo ->
                        Box(
                            modifier = Modifier
                                .size(columnWidth)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                                .clickable {
                                    selectedPhoto.value = photo
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = photo.model),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("Fecha", "Nombre", "Tamaño", "Tipo")
                    filters.forEach { filter ->
                        Button(
                            onClick = {
                                when (filter) {
                                    "Fecha" -> photos.value = photos.value.sortedBy { it.modificationDate }
                                    "Nombre" -> photos.value = photos.value.sortedBy { it.fileName }
                                    "Tamaño" -> photos.value = photos.value.sortedBy { it.fileSize }
                                    "Tipo" -> photos.value = photos.value.sortedBy { it.fileType }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                .background(Color.LightGray) // Fondo del botón
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.Black, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 12.sp) // Estilo del texto

                            )
                        }
                    }
                }
            }
        }

        // Pantalla modal para mostrar la imagen seleccionada
        selectedPhoto.value?.let { photo ->
            ImageFullScreen(photo = photo) {
                selectedPhoto.value = null // Cierra la pantalla modal al hacer clic fuera de la imagen
            }
        }
    }
}
@Composable
fun GaleriaScreenshot(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad de la imagen seleccionada
    val selectedScreenshot = remember { mutableStateOf<screenshot?>(null) }

    // Estado para controlar la lista de fotos mostradas
    val screenshots = remember { mutableStateOf(randomSizedscreenshot) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = screenshots.value.chunked(2) // Agrupa las fotos en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rows[rowIndex].forEach { screenshot ->
                        Column(
                            modifier = Modifier
                                .width(columnWidth)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                                .clickable {
                                    selectedScreenshot.value = screenshot
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = screenshot.model),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxSize()
                            )

                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("Fecha", "Nombre", "Tamaño", "Tipo")
                    filters.forEach { filter ->
                        Button(
                            onClick = {
                                when (filter) {
                                    "Fecha" -> screenshots.value = screenshots.value.sortedBy { it.modificationDate }
                                    "Nombre" -> screenshots.value = screenshots.value.sortedBy { it.fileName }
                                    "Tamaño" -> screenshots.value = screenshots.value.sortedBy { it.fileSize }
                                    "Tipo" -> screenshots.value = screenshots.value.sortedBy { it.fileType }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                .background(Color.LightGray) // Fondo del botón
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.Black, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 12.sp) // Estilo del texto
                            )
                        }
                    }
                }
            }
        }

        // Pantalla modal para mostrar la imagen seleccionada
        selectedScreenshot.value?.let { screenshot ->
            ScreenshotFullScreen(screenshot = screenshot) {
                selectedScreenshot.value = null // Cierra la pantalla modal al hacer clic fuera de la imagen
            }
        }
    }
}

@Composable
fun ScreenshotFullScreen(screenshot: screenshot, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = screenshot.model),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}



@Composable
fun GaleriaVideos(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad del video seleccionado
    val selectedVideo = remember { mutableStateOf<Video?>(null) }

    // Estado para controlar la lista de videos mostrados
    val videosState = remember { mutableStateOf(videos) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = videosState.value.chunked(2) // Agrupa los videos en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rows[rowIndex].forEach { video ->
                        Box(
                            modifier = Modifier
                                .size(columnWidth)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray)
                                .clickable {
                                    selectedVideo.value = video
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            // Indicador visual del video, por ejemplo, un ícono de reproducción
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                                contentDescription = "Play Video",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(56.dp)
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("Fecha", "Nombre", "Tamaño", "Tipo")
                    filters.forEach { filter ->
                        Button(
                            onClick = {
                                when (filter) {
                                    "Fecha" -> videosState.value = videosState.value.sortedBy { it.modificationDate }
                                    "Nombre" -> videosState.value = videosState.value.sortedBy { it.fileName }
                                    "Tamaño" -> videosState.value = videosState.value.sortedBy { it.fileSize }
                                    "Tipo" -> videosState.value = videosState.value.sortedBy { it.fileType }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                .background(Color.LightGray) // Fondo del botón
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.Black, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 12.sp) // Estilo del texto
                            )
                        }
                    }
                }
            }
        }

        // Pantalla modal para mostrar el video seleccionado
        selectedVideo.value?.let { video ->
            VideoFullScreen(video = video) {
                selectedVideo.value = null // Cierra la pantalla modal al hacer clic fuera del video
            }
        }
    }
}

@Composable
fun VideoFullScreen(video: Video, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + video.videoResId))
                    start()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun GaleriaMedios(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad del medio seleccionado
    val selectedMedia = remember { mutableStateOf<Any?>(null) }

    // Estado para controlar la lista de medios mostrados
    val mediaItems = remember {
        mutableListOf<Any>().apply {
            addAll(randomSizedPhotos)
            addAll(randomSizedscreenshot)
            addAll(videos)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = mediaItems.chunked(2) // Agrupa los medios en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rows[rowIndex].forEach { media ->
                        when (media) {
                            is Photo -> {
                                MediaItem(
                                    model = media.model,
                                    columnWidth = columnWidth,
                                    onClick = { selectedMedia.value = media }
                                )
                            }
                            is screenshot -> {
                                MediaItem(
                                    model = media.model,
                                    columnWidth = columnWidth,
                                    onClick = { selectedMedia.value = media }
                                )
                            }
                            is Video -> {
                                VideoThumbnail(
                                    video = media,
                                    columnWidth = columnWidth,
                                    onClick = { selectedMedia.value = media }
                                )
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filters = listOf("Fecha", "Nombre", "Tamaño", "Tipo")
                    filters.forEach { filter ->
                        Button(
                            onClick = {
                                when (filter) {
                                    "Fecha" -> mediaItems.sortBy { media ->
                                        when (media) {
                                            is Photo -> media.creationDate
                                            is screenshot -> media.creationDate
                                            is Video -> media.creationDate
                                            else -> null
                                        }
                                    }
                                    "Nombre" -> mediaItems.sortBy { media ->
                                        when (media) {
                                            is Photo -> media.fileName
                                            is screenshot -> media.fileName
                                            is Video -> media.fileName
                                            else -> null
                                        }
                                    }
                                    "Tamaño" -> mediaItems.sortBy { media ->
                                        when (media) {
                                            is Photo -> media.fileSize
                                            is screenshot -> media.fileSize
                                            is Video -> media.fileSize
                                            else -> null
                                        }
                                    }
                                    "Tipo" -> mediaItems.sortBy { media ->
                                        when (media) {
                                            is Photo -> media.fileType
                                            is screenshot -> media.fileType
                                            is Video -> media.fileType
                                            else -> null
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
                                .background(Color.LightGray) // Fondo del botón
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.Black, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 12.sp) // Estilo del texto
                            )
                        }
                    }
                }
            }
        }

        // Pantalla modal para mostrar el medio seleccionado
        selectedMedia.value?.let { media ->
            when (media) {
                is Photo -> {
                    MediaFullScreen(
                        model = media.model,
                        onClose = { selectedMedia.value = null }
                    )
                }
                is screenshot -> {
                    MediaFullScreen(
                        model = media.model,
                        onClose = { selectedMedia.value = null }
                    )
                }
                is Video -> {
                    VideoFullScreenn(
                        video = media,
                        onClose = { selectedMedia.value = null }
                    )
                }
            }
        }
    }
}

@Composable
private fun MediaItem(model: Int, columnWidth: Dp, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(columnWidth)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = model),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxSize()
        )
    }
}

@Composable
private fun VideoThumbnail(video: Video, columnWidth: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(columnWidth)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_filter_list_24),
            contentDescription = "Play Video",
            tint = Color.White,
            modifier = Modifier
                .size(56.dp)
        )
    }
}

@Composable
private fun MediaFullScreen(model: Int, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = model),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
private fun VideoFullScreenn(video: Video, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                VideoView(context).apply {
                    setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + video.videoResId))
                    start()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun SoftwareSpecsScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_info_outline_24),
                        contentDescription = "Info Icon",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Google Pixel Watch 2 - Especificaciones del Software",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        val specs = listOf(
            "Sistema Operativo: Wear OS 4",
            "Procesador: Qualcomm Snapdragon Wear 4100+",
            "RAM: 1GB",
            "Almacenamiento: 8GB",
            "Conectividad: Wi-Fi, Bluetooth, NFC",
            "Sensores: Acelerómetro, Giroscopio, Barómetro, Sensor de ritmo cardíaco"
        )

        items(specs.size) { index ->
            Text(
                text = specs[index],
                color = Color.Gray,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .alpha(animatedAlpha)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Volver",
                color = Color.Cyan,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(12.dp)

            )
        }
    }
}

//Pizarra
@Composable
fun CanvasDrawingBoard(navController: NavHostController) {
    var paths by remember { mutableStateOf(emptyList<Pair<Path, Color>>()) }
    var currentPath by remember { mutableStateOf(Path()) }
    var currentColor by remember { mutableStateOf(Color.Black) }
    var isDrawing by remember { mutableStateOf(false) }
    var drawTriangleMode by remember { mutableStateOf(false) }
    var drawSquareMode by remember { mutableStateOf(false) }
    var drawCircleMode by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(11.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularColorButton(Color.Black) { color -> currentColor = color }
            CircularColorButton(Color.Red) { color -> currentColor = color }
            CircularColorButton(Color.Green) { color -> currentColor = color }
            CircularColorButton(Color.Blue) { color -> currentColor = color }

            CircularButton(
                onClick = { paths = emptyList() },
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 1.dp)
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            color = Color.White
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                isDrawing = true
                                when {
                                    drawTriangleMode -> drawTriangle(
                                        currentPath,
                                        currentColor,
                                        offset
                                    )

                                    drawSquareMode -> drawSquare(currentPath, currentColor, offset)
                                    drawCircleMode -> drawCircle(currentPath, currentColor, offset)
                                    else -> currentPath = Path().apply {
                                        moveTo(offset.x, offset.y)
                                    }
                                }
                            },
                            onDragEnd = {
                                if (isDrawing) {
                                    isDrawing = false
                                    if (!drawTriangleMode && !drawSquareMode && !drawCircleMode) {
                                        paths = paths + (currentPath to currentColor)
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                if (isDrawing) {
                                    val position = change.position
                                    when {
                                        drawTriangleMode -> drawTriangle(
                                            currentPath,
                                            currentColor,
                                            position
                                        )

                                        drawSquareMode -> drawSquare(
                                            currentPath,
                                            currentColor,
                                            position
                                        )

                                        drawCircleMode -> drawCircle(
                                            currentPath,
                                            currentColor,
                                            position
                                        )

                                        else -> currentPath.lineTo(position.x, position.y)
                                    }
                                    change.consumeAllChanges()
                                }
                            }
                        )
                    }
            ) {
                paths.forEach { (path, color) ->
                    drawPath(path, color)
                }
                if (isDrawing && !drawTriangleMode && !drawSquareMode && !drawCircleMode) {
                    drawPath(currentPath, currentColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TriangleButton(
                color = currentColor,
                onClick = {
                    drawTriangleMode = !drawTriangleMode
                    drawSquareMode = false
                    drawCircleMode = false
                    if (!drawTriangleMode) {
                        paths = paths + (currentPath to currentColor)
                    }
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            SquareButton(
                color = currentColor,
                onClick = {
                    drawSquareMode = !drawSquareMode
                    drawTriangleMode = false
                    drawCircleMode = false
                    if (!drawSquareMode) {
                        paths = paths + (currentPath to currentColor)
                    }
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            CircularButton(
                onClick = {
                    drawCircleMode = !drawCircleMode
                    drawTriangleMode = false
                    drawSquareMode = false
                    if (!drawCircleMode) {
                        paths = paths + (currentPath to currentColor)
                    }
                },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

private fun drawTriangle(path: Path, color: Color, position: Offset? = null) {
    val sideLength = 100f  // Aumentamos la longitud del lado del triángulo
    path.reset()
    val halfSideLength = sideLength / 2
    val height = sideLength * Math.sqrt(3.0) / 2
    val x = position?.x ?: 100f
    val y = position?.y ?: 100f

    path.moveTo(x, y - (2 * height / 3).toFloat()) // Ajustamos la posición del triángulo
    path.lineTo(x - halfSideLength, y + (height / 3).toFloat())
    path.lineTo(x + halfSideLength, y + (height / 3).toFloat())
    path.close()
}

private fun drawSquare(path: Path, color: Color, position: Offset? = null) {
    val sideLength = 50f
    path.reset()
    if (position != null) {
        path.moveTo(position.x - sideLength / 2, position.y - sideLength / 2)
        path.lineTo(position.x + sideLength / 2, position.y - sideLength / 2)
        path.lineTo(position.x + sideLength / 2, position.y + sideLength / 2)
        path.lineTo(position.x - sideLength / 2, position.y + sideLength / 2)
    } else {
        path.moveTo(100f - sideLength / 2, 100f - sideLength / 2)
        path.lineTo(100f + sideLength / 2, 100f - sideLength / 2)
        path.lineTo(100f + sideLength / 2, 100f + sideLength / 2)
        path.lineTo(100f - sideLength / 2, 100f + sideLength / 2)
    }
    path.close()
}

private fun drawCircle(path: Path, color: Color, position: Offset? = null) {
    val radius = 25f
    path.reset()
    val centerX = position?.x ?: 100f
    val centerY = position?.y ?: 100f
    path.addOval(Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius))
}

@Composable
fun CircularColorButton(color: Color, onClick: (Color) -> Unit) {
    Surface(
        color = color,
        shape = CircleShape,
        modifier = Modifier
            .size(20.dp)
            .padding(1.dp)
            .clickable(onClick = { onClick(color) })
    ) {
        // Optional: Add content inside Surface if needed
    }
}

@Composable
fun CircularButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        color = Color.Gray,
        shape = CircleShape,
        modifier = modifier
            .size(20.dp)
            .clickable(onClick = onClick)
    ) {
        // Optional: Add content inside Surface if needed
    }
}

@Composable
fun TriangleButton(color: Color, onClick: () -> Unit) {
    Canvas(modifier = Modifier
        .size(24.dp)
        .clickable(onClick = onClick)
    ) {
        val path = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path, color)
    }
}

@Composable
fun SquareButton(color: Color, onClick: () -> Unit) {
    Canvas(modifier = Modifier
        .size(24.dp)
        .clickable(onClick = onClick)
    ) {
        val sideLength = size.minDimension
        drawRect(
            color = color,
            topLeft = Offset.Zero,
            size = androidx.compose.ui.geometry.Size(sideLength, sideLength)
        )
    }
}


/*

@Preview(device = WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AppNavigation()
    // Uso actualizado de WearDevices.SMALL_ROUND




}*/