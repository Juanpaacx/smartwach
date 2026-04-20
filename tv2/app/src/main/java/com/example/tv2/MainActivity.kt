package com.example.tv2

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.tv.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.tv2.ui.theme.Tv2Theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState

import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tv2Theme {


                    AppNavigation()


            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "RutaHome") {
        composable(route = "RutaHome") {
            MainScreen(navController)
        }
       composable(route = "RutaApps") {
            WearAppp(navController)
        }
        composable(route = "RutaReproductor") {
            MusicPlayerScreen(navController)
        }

        /*  composable(route = "RutaAcerca") {
             SoftwareSpecsScreen(navController)
         }*/


    }


}

/*
@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainContent(navController) }
        composable("all") { /* Pantalla de "All" */ }
        composable("camara") { /* Pantalla de "Camara" */ }
        composable("screenshot") { /* Pantalla de "Screenshot" */ }
        composable("videos") { /* Pantalla de "Videos" */ }
        composable("music") { /* Pantalla de "Music" */ }
        composable("settings") { /* Pantalla de "Settings" */ }
    }
}
*/
/*
@Composable
fun MainScreen() {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://${context.packageName}/raw/fondo"))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = android.widget.FrameLayout.LayoutParams(
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose { exoPlayer.release() }
    }

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
            .background(Color.Transparent),
        contentAlignment = Alignment.CenterStart
    ) {
        // Columna para la hora y fecha alineada a la izquierda
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp) // Asegurarse de que la columna ocupe todo el alto y tenga un padding desde la izquierda
        ) {
            // Números de la hora y los minutos
            Row(
                horizontalArrangement = Arrangement.Start,
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

            Spacer(modifier = Modifier.height(16.dp))

            // Información del clima
            Text(
                text = "Clima: Soleado, 25°C",
                color = Color(192, 192, 192), // Color LightGray en RGB
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
*/
/*
@Composable
fun MainScreen() {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://${context.packageName}/raw/fondo"))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = android.widget.FrameLayout.LayoutParams(
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose { exoPlayer.release() }
    }

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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            // Primera pantalla con fondo de video, hora y clima dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                // Columna para la hora y fecha alineada a la izquierda
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp) // Asegurarse de que la columna ocupe todo el alto y tenga un padding desde la izquierda
                ) {
                    // Números de la hora y los minutos
                    Row(
                        horizontalArrangement = Arrangement.Start,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del clima
                    Text(
                        text = "Clima: Soleado, 25°C",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }

        item {
            // Segunda pantalla completa dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Segunda Pantalla",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}*/
/*
@Composable
fun MainScreen() {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlaer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://${context.packageName}/raw/fondo"))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player =exoPlayer
                    useController = false
                    layoutParams = android.widget.FrameLayout.LayoutParams(
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose { exoPlayer.release() }
    }

    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val localTime = LocalTime.now()
            val localDate = LocalDate.now()
            currentTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            currentDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            hours.value = localTime.hour.toString()
            minutes.value = localTime.minute.toString()
            delay(1000) // Actualiza la hora cada segundo
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            // Primera pantalla con fondo de video, hora y clima dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                // Columna para la hora, fecha y clima alineada a la izquierda
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp) // Asegurarse de que la columna ocupe todo el alto y tenga un padding desde la izquierda
                ) {
                    // Números de la hora y los minutos
                    Row(
                        horizontalArrangement = Arrangement.Start,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha actual
                    Text(
                        text = "Fecha: $currentDate",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del clima
                    Text(
                        text = "Clima: Nublado, 21°C",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
        item {
            // Segunda pantalla completa dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                MusicPlayerScreen()
            }
        }
    }
}*/

/* esta esta bien pero le faltaba wor de apps
@Composable
fun MainScreen() {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://${context.packageName}/raw/fondo"))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    // Ensure ExoPlayer resources are released
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    // Display ExoPlayer video
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val localTime = LocalTime.now()
            val localDate = LocalDate.now()
            currentTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            currentDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            hours.value = localTime.hour.toString()
            minutes.value = localTime.minute.toString()
            delay(1000) // Actualiza la hora cada segundo
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            // Primera pantalla con fondo de video, hora y clima dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                // Columna para la hora, fecha y clima alineada a la izquierda
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp) // Asegurarse de que la columna ocupe todo el alto y tenga un padding desde la izquierda
                ) {
                    // Números de la hora y los minutos
                    Row(
                        horizontalArrangement = Arrangement.Start,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha actual
                    Text(
                        text = "Fecha: $currentDate",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del clima
                    Text(
                        text = "Clima: Nublado, 21°C",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

        }

        item {
            // Segunda pantalla completa dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                MusicPlayerScreen()
            }
        }
    }
}
*/
@Composable
fun MusicPlayerScreen(navController: NavController) {
    val context = LocalContext.current
    val genre = remember { genres.first() }

    SongScreen(
        genre = genre,
        playSong = { resId -> playOrPauseSong(context, resId) }
    )
}

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

@Composable
fun SongScreen(
    genre: Genre,
    playSong: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = genre.name,
            color = Color.Gray,
            fontSize = 24.sp,
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
            verticalArrangement = Arrangement.Top
        ) {
            genre.songs.forEach { song ->
                SongItem(song = song, playSong = playSong)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

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

/*galeria*/

/*
@Composable
fun PaginaUno(navController: NavController) {
    val listState: ScalingLazyListState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
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

@Composable
fun RoundedBlurImageButton(
    image: Painter,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable(onClick = onClick)
            .shadow(8.dp, CircleShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
*/


@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://${context.packageName}/raw/fondo"))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            volume = 0f // Mute the video
        }
    }

    // Ensure ExoPlayer resources are released
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    // Display ExoPlayer video
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                layoutParams = android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    val hours = remember { mutableStateOf("") }
    val minutes = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            val localTime = LocalTime.now()
            val localDate = LocalDate.now()
            currentTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            currentDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            hours.value = localTime.hour.toString()
            minutes.value = localTime.minute.toString()
            delay(1000) // Actualiza la hora cada segundo
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            // Primera pantalla con fondo de video, hora y clima dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterStart
            ) {
                // Columna para la hora, fecha y clima alineada a la izquierda
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp) // Asegurarse de que la columna ocupe todo el alto y tenga un padding desde la izquierda
                ) {
                    // Números de la hora y los minutos
                    Row(
                        horizontalArrangement = Arrangement.Start,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fecha actual
                    Text(
                        text = "Fecha: $currentDate",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del clima
                    Text(
                        text = "Clima: Nublado, 24°C",
                        color = Color(192, 192, 192), // Color LightGray en RGB
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Fila con 6 apartados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ChipCalorias(navController)
                        ChipYoutube(navController)
                        // Aquí puedes agregar más botones o elementos si es necesario
                    }
                }
            }
        }

        item {
            // Segunda pantalla completa dentro de un Box
            Box(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
     /*           MusicPlayerScreen()*/
            }
        }
    }
}

@Composable
fun ChipYoutube(navController: NavController) {
    Card(
        modifier = Modifier
            .width(200.dp) // Aumenta el ancho para hacerlo más accesible
            .height(150.dp) // Aumenta la altura para facilitar la selección
            .padding(8.dp)
            .clickable { navController.navigate("RutaReproduCtor") }
            .focusable(), // Permite que el card sea enfocable en la TV
        elevation = 1.dp,
        backgroundColor = Color.Gray.copy(alpha = 0.12f) // Fondo gris con opacidad para un buen contraste
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.musicaa),
                contentDescription = "MUSICA",
                modifier = Modifier
                    .size(160.dp) // Aumenta el tamaño de la imagen para una mejor visibilidad
                    .wrapContentSize(align = Alignment.Center)
            )
        }
    }
}

@Composable
fun ChipCalorias(navController: NavController) {
    Card(
        modifier = Modifier
            .width(200.dp) // Aumenta el ancho para hacerlo más accesible
            .height(150.dp) // Aumenta la altura para facilitar la selección
            .padding(8.dp)
            .clickable { navController.navigate("RutaApps") }
            .focusable(), // Permite que el card sea enfocable en la TV
        elevation = 1.dp,
        backgroundColor = Color.Gray.copy(alpha = 0.12f) // Fondo gris con opacidad para un buen contraste
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.galeriaa),
                contentDescription = "Calorias",
                modifier = Modifier
                    .size(160.dp) // Aumenta el tamaño de la imagen para una mejor visibilidad
                    .wrapContentSize(align = Alignment.Center)
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
        model =R.drawable.camara3,  // Dejar en blanco
        fileName = "camara3.jpg",
        creationDate = "2023-01-01",
        modificationDate = "2023-06-01",
        fileType = "JPG",
        fileSize = "2MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model =R.drawable.camara4,  // Dejar en blanco
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
        model = R.drawable.camara9,  // Dejar en blanco
        fileName = "camara9.jpg",
        creationDate = "2023-06-01",
        modificationDate = "2023-06-06",
        fileType = "PNG",
        fileSize = "2.5MB",
        tags = listOf("Camara", "All")
    ),
    Photo(
        model = R.drawable.camara10,  // Dejar en blanco
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
        model =  R.drawable.captura7,  // Dejar en blanco
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
/*
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
            .height(500.dp) // Aumenta la altura para mejor visibilidad en TV
            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para un diseño elegante
            .background(Color.Black.copy(alpha = 0.3f)) // Fondo negro semi-transparente
            .clickable { onClick() }
            .border(2.dp, Color.White, RoundedCornerShape(16.dp)) // Borde blanco para destacar el botón
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
                .background(Color.Black.copy(alpha = 0.3f)) // Fondo de la imagen semi-transparente
        ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 0.8f // Aplicar un ligero efecto de desenfoque
                    }
                    .background(Color.Black.copy(alpha = 0.5f)) // Fondo negro semi-transparente para la imagen
            )
        }
        Text(
            text = text,
            fontSize = 24.sp, // Tamaño de la fuente grande para mejor visibilidad
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(16.dp)
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
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp), // Añadir padding para mayor separación
        verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre elementos
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
                },
                modifier = Modifier.padding(vertical = 8.dp) // Añadir padding vertical entre botones
            )
        }
    }
}

*/
@Composable
fun PaginaUno(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Colección",
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val (imageResource1, buttonText1) = painterResource(id = R.drawable.captura9) to "All"
            val (imageResource2, buttonText2) = painterResource(id = R.drawable.camara1) to "Camera"

            RoundedBlurImageButton(
                image = imageResource1,
                text = buttonText1,
                onClick = { navController.navigate("all") },
                modifier = Modifier.weight(1f)
            )
            RoundedBlurImageButton(
                image = imageResource2,
                text = buttonText2,
                onClick = { navController.navigate("camara") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val (imageResource3, buttonText3) = painterResource(id = R.drawable.camara10) to "Screenshot"
            val (imageResource4, buttonText4) = painterResource(id = R.drawable.captura6) to "Videos"

            RoundedBlurImageButton(
                image = imageResource3,
                text = buttonText3,
                onClick = { navController.navigate("screenshot") },
                modifier = Modifier.weight(1f)
            )
            RoundedBlurImageButton(
                image = imageResource4,
                text = buttonText4,
                onClick = { navController.navigate("videos") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun RoundedBlurImageButton(
    image: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(34.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable { onClick() }
            .border(2.dp, Color.Transparent, RoundedCornerShape(16.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(17.dp))
                .padding(16.dp)
        )
    }
}

@Composable
fun WearAppp(navController: NavHostController) {
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
        }
    }
}

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
    val columnWidth = (screenWidth / 4) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad de la imagen seleccionada
    val selectedPhoto = remember { mutableStateOf<Photo?>(null) }

    // Estado para controlar la lista de fotos mostradas
    val photos = remember { mutableStateOf(randomSizedPhotos) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fondo negro
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = photos.value.chunked(4) // Agrupa las fotos en pares para dos columnas

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
                                .background(Color.Black)
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
                        .padding(horizontal = 16.dp, vertical = 8.dp), // Ajusta los márgenes
                    horizontalArrangement = Arrangement.Center // Centra los filtros
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
                                .padding(horizontal = 16.dp, vertical = 8.dp) // Margen interno
                                .weight(1f), // Asegura que los botones se distribuyan igualmente
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.Black, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 14.sp) // Estilo del texto
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
    val columnWidth = (screenWidth / 4) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad de la imagen seleccionada
    val selectedScreenshot = remember { mutableStateOf<screenshot?>(null) }

    // Estado para controlar la lista de capturas de pantalla mostradas
    val screenshots = remember { mutableStateOf(randomSizedscreenshot) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado aumentado para mejor separación
        ) {
            val rows = screenshots.value.chunked(4) // Agrupa las capturas de pantalla en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado aumentado entre columnas
                ) {
                    rows[rowIndex].forEach { screenshot ->
                        Box(
                            modifier = Modifier
                                .width(columnWidth)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados más marcados
                                .background(Color.Black) // Fondo más oscuro para un mejor contraste
                                .clickable {
                                    selectedScreenshot.value = screenshot
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = screenshot.model),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Aumenta la altura para mayor visibilidad
                        .padding(6.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.Center // Centra los botones
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
                                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                                .background(Color.Black) // Fondo del botón
                                .padding(horizontal = 32.dp, vertical = 16.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.White, // Color del texto para mejor contraste
                                style = MaterialTheme.typography.button.copy(fontSize = 20.sp) // Estilo del texto
                            )
                        }
                    }
                }
            }
        }

        // Pantalla modal para mostrar la captura de pantalla seleccionada
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
            .background(Color.Black.copy(alpha = 0.9f)) // Fondo más oscuro para mayor contraste
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = screenshot.model),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
fun GaleriaVideos(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 4) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad del video seleccionado
    val selectedVideo = remember { mutableStateOf<Video?>(null) }

    // Estado para controlar la lista de videos mostrados
    val videosState = remember { mutableStateOf(videos) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre filas
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp) // Padding alrededor
        ) {
            val rows = videosState.value.chunked(4) // Agrupa los videos en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre elementos
                ) {
                    rows[rowIndex].forEach { video ->
                        Box(
                            modifier = Modifier
                                .width(columnWidth)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black)
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
                                    .size(72.dp) // Aumenta el tamaño del ícono para mejor visibilidad
                            )
                        }
                    }
                }
            }

            item {
                // Fila de filtros
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre botones
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
                                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                                .background(Color.Black) // Fondo del botón
                                .padding(horizontal = 20.dp, vertical = 12.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.White, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 16.sp) // Estilo del texto
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
fun GaleriaMedios(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 4) - 16.dp // Ancho aproximado para dos columnas

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

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado entre filas
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp) // Padding alrededor
        ) {
            val rows = mediaItems.chunked(4) // Agrupa los medios en pares para dos columnas

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre elementos
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
                // Fila de filtros
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Altura ajustada para mayor visibilidad
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center // Centrando los filtros
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
                                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                                .background(Color.Black) // Fondo del botón
                                .padding(horizontal = 24.dp, vertical = 12.dp), // Margen interno
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) // Color de fondo del botón
                        ) {
                            Text(
                                text = filter,
                                color = Color.White, // Color del texto
                                style = MaterialTheme.typography.button.copy(fontSize = 18.sp) // Estilo del texto
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
                    VideoFullScreen(
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
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
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
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_filter_list_24),
            contentDescription = "Play Video",
            tint = Color.White,
            modifier = Modifier
                .size(72.dp) // Aumenta el tamaño del ícono para mejor visibilidad
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
                .padding(32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Composable
private fun VideoFullScreen(video: Video, onClose: () -> Unit) {
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
