package com.example.lazybtn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.lazybtn.presentation.theme.LazyBtnTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyBtnTheme {
                Contenedor()
            }
        }
    }
}

data class Botones(val id: Int, val rotulo: String)

@Composable
fun ListaBotones() {
    val listaB = List(size = 10) { Botones(id = it, rotulo = "Botón $it") }
    val botonSeleccionado = remember { mutableStateOf<Botones?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lista de Botones",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listaB) { button ->

                Button(
                    onClick = { botonSeleccionado.value = button },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (botonSeleccionado.value == button) Color.Gray else Color.Blue
                    )
                ) {
                    Text(text = button.rotulo)
                }
            }
        }


        botonSeleccionado.value?.let { selected ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Botón seleccionado: ${selected.rotulo}")
        }
    }
}

@Composable
fun Contenedor() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListaBotones()
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LazyBtnTheme {
        Contenedor()
    }
}
