/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.music.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalContentAlpha
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.music.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            MusicPlayerScreen()
        }
    }
}

@Composable
fun MusicPlayerScreen() {
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
                    iconId = R.drawable.baseline_airplay_24,
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

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MusicPlayerScreen()
}