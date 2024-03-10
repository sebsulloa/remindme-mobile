package com.misw.remindme.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.misw.remindme.R
import com.misw.remindme.ui.composables.RemindMeBottomBar
import com.misw.remindme.ui.composables.RemindMeTopBar
import com.misw.remindme.ui.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VoiceAlarmScreen(navController: NavController) {
    var isListening by remember { mutableStateOf(true) }
    var alarmText by remember { mutableStateOf("Escuchando tu\nalarma...") }

    Scaffold(
        topBar = {
            RemindMeTopBar(
                title = "Crear Alarma",
                showBackButton = true,
                navController = navController
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isListening) {
                    showDialog(navController = navController)
                }
                Text(
                    text = alarmText,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.mic_24_big),
                    contentDescription = "Alarm Image",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        isListening = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(text = "Detener")
                }
            }
        },
        bottomBar = { RemindMeBottomBar(navController) },
    )
}

@Composable
fun showDialog(navController: NavController) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        onDismissRequest = {},
        title = { Text(text = "¡Alarma creada!") },
        text = {
            Column {
                Text(
                    text = "Recoger a los niños en el colegio a las 5pm el 26 de marzo",
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Navigate back to the previous screen
                    navController.popBackStack()
                }
            ) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    // Navigate to the edit alarm screen
                    navController.popBackStack()
                    navController.navigate(Screen.EditAlarm.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Editar")
            }
        },
    )
}
