/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.pantalla.presentation

import android.media.Image
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.pantalla.R
import com.example.pantalla.presentation.theme.PantallaTheme
import com.google.firebase.perf.util.Timer
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.unit.dp

import com.example.myapp.ui.theme.MyAppTheme
import java.text.SimpleDateFormat
import java.util.*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    fondos()
                }
            }

        }
    }
}


@Composable
fun fondos() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Painter = painterResource(id = R.drawable.fondo),
                contentScale = ContentScale.FillBounds
            )
    ) {
        // Add more views here!
        Text(
            text = "Hello Stack!",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Display the current time at the bottom
        val currentTime by currentTime().observeAsState(initial = "")
        Text(
            text = currentTime,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun currentTime(): androidx.lifecycle.LiveData<String> {
    val liveData = androidx.lifecycle.MutableLiveData<String>()
    val timeFormat = SimpleDateFormat("HH:mm:ss z", Locale.getDefault())
    val timer = Timer()
    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            liveData.postValue(timeFormat.format(Date()))
        }
    }, 0, 1000)
    return liveData
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    fondos()
}