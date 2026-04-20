/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.galeria.presentation


import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.galeria.R

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
                    .blur(16.dp) // Apply blur effect
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
}*/
//https://developer.android.com/develop/ui/compose/lists?hl=es-419
//https://developer.android.com/develop/ui/compose/animation/shared-elements/customize?hl=es-419
//https://developer.android.com/develop/ui/compose/animation/shared-elements/additional-samples?hl=es-419
//https://m3.material.io/components/bottom-app-bar/guidelines
/*@Composable
fun WearApp() {
    val listState: ScalingLazyListState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(4) { index ->
            val imageResource = when (index) {
                0 -> painterResource(id = R.drawable.camara1) // Cambiar a tus recursos PNG
                1 -> painterResource(id = R.drawable.camara1)
                2 -> painterResource(id = R.drawable.camara1)
                3 -> painterResource(id = R.drawable.camara1)
                else -> painterResource(id = R.drawable.camara1)
            }
            RoundedBlurImageButton(
                image = imageResource,
                text = "Button $index",
                onClick = { /* Handle click */ }
            )
        }
    }
}
*/


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
fun WearApp() {
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



@Composable
fun PaginaTres(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Esta es la Página Tres")
    }
}

@Composable
fun PaginaCuatro(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Esta es la Página Cuatro")
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

/*
@Composable
fun GaleriaCamara(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val rows = randomSizedPhotos.chunked(2) // Agrupa las fotos en pares para dos columnas

        items(rows.size) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                rows[rowIndex].forEach { photo ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray)
                            .clickable {
                                navController.navigate("DetalleImagen/${photo.fileName}")
                            }
                    ) {
                        Image(
                            painter = painterResource(id = photo.model),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
*/
/*
@Composable
fun GaleriaCamara(navController: NavController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val columnWidth = (screenWidth / 2) - 8.dp // Ancho aproximado para dos columnas

    // Estado para controlar la visibilidad de la imagen seleccionada
    val selectedPhoto = remember { mutableStateOf<Photo?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val rows = randomSizedPhotos.chunked(2) // Agrupa las fotos en pares para dos columnas

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
        }

        // Pantalla modal para mostrar la imagen seleccionada
        selectedPhoto.value?.let { photo ->
            ImageFullScreen(photo = photo) {
                selectedPhoto.value = null // Cierra la pantalla modal al hacer clic fuera de la imagen
            }
        }

        FloatingActionButton(
            onClick = { /* Acción del botón */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = "Agregar"
            )
        }
    }
}


*/
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
//ESte codigo y el de arriba si funcionan bien pro sin mostrar los detalles de la imagen

/*
@Composable
fun ImageFullScreen(photo: Photo, onClose: () -> Unit) {
    // Estado para controlar si se muestran los detalles de la imagen
    val showDetails = remember { mutableStateOf(true) } // Inicialmente muestra los detalles

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = photo.model),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los detalles de la imagen directamente
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Text(
                        text = "Nombre de archivo: ${photo.fileName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Text(
                        text = "Fecha de creación: ${photo.creationDate}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Agrega más detalles según sea necesario
            }
        }
    }
}

*/


/*
@Composable
fun ImageFullScreen(photo: Photo, onClose: () -> Unit) {
    // Estado para controlar si se muestran los detalles de la imagen
    val showDetails = remember { mutableStateOf(true) } // Inicialmente muestra los detalles

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = photo.model),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los detalles de la imagen directamente
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Text(
                        text = "Nombre de archivo: ${photo.fileName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Text(
                        text = "Fecha de creación: ${photo.creationDate}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Agrega más detalles según sea necesario
            }
        }
    }
}
*/

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
/*@Composable
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
*/

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

/*
@Composable
fun rememberVideoThumbnail(videoResId: Int): State<Painter?> {
    return remember(videoResId) {
        mutableStateOf<Painter?>(null)
    }.apply {
        LaunchedEffect(videoResId) {
            val thumbnail = loadVideoThumbnail(videoResId)
            value = thumbnail
        }
    }
}

private suspend fun loadVideoThumbnail(videoResId: Int): Painter? {
    return withContext(Dispatchers.IO) {
        try {
            val retriever = MediaMetadataRetriever()
            val uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + videoResId)
            retriever.setDataSource(LocalContext.current, uri)
            val frame = retriever.getFrameAtTime(0L, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            frame?.let { bitmap ->
                return@withContext rememberPainter(bitmap = bitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}
@Composable
fun VideoThumbnail(video: Video) {
    val thumbnailPainter = rememberVideoThumbnail(video.videoResId)
    thumbnailPainter.value?.let { painter ->
        Image(
            painter = painter,
            contentDescription = "Video Thumbnail",
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .fillMaxSize()
        )
    }
}*/
/*
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
                            // Mostrar miniatura del video
                            VideoThumbnail(video = video)
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
*/
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



@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}