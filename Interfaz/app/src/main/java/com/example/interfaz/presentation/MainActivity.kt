/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.interfaz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.interfaz.R
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.unit.sp

import androidx.wear.compose.material.Icon

import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape



import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Scaffold

import androidx.compose.ui.res.painterResource


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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
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
                    iconId = R.drawable.skip_previous,
                    description = "Previous"
                )
                ControlButton(
                    onClick = { /* Play/Pause action */ },
                    iconId = R.drawable.play_arrow,
                    description = "Play",
                    backgroundColor = Color.Magenta
                )
                ControlButton(
                    onClick = { /* Next track action */ },
                    iconId = R.drawable.skip_next,
                    description = "Next"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ControlButton(
                    onClick = { /* Like action */ },
                    iconId = R.drawable.favorite,
                    description = "Like"
                )
                ControlButton(
                    onClick = { /* Volume action */ },
                    iconId = R.drawable.volume_up,
                    description = "Volume"
                )
            }
        }
    }
}

@Composable
fun ControlButton(
    onClick: () -> Unit,
    iconId: Int,
    description: String,
    backgroundColor: Color = Color.Transparent
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.size(48.dp),
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource (id = iconId),
            contentDescription = description,
            tint = Color.White
        )
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
MusicPlayerScreen()
}