package com.misw.remindme.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.misw.remindme.ui.composables.RemindMeBottomBar
import com.misw.remindme.ui.composables.RemindMeTopBar
import com.misw.remindme.ui.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserConfigScreen(navController: NavController) {
    val randomName = "Elena Sanchez"
    val email = "elena@example.com"

    Scaffold(
        bottomBar = { RemindMeBottomBar(navController) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            RemindMeTopBar(showBackButton = true, navController = navController)

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                // Text field for name
                OutlinedTextField(
                    value = randomName,
                    onValueChange = {},
                    label = { Text("Nombre") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Text field for email
                OutlinedTextField(
                    value = email,
                    onValueChange = {},
                    label = { Text("Correo electrónico") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cerrar Sesión button
                Button(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.Login.route)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(text = "Cerrar Sesión")
                }
            }
        }
    }
}
