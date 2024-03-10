package com.misw.remindme.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.misw.remindme.R
import com.misw.remindme.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }

        // Logo
        Image(
            painter = painterResource(id = R.drawable.bell1),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "RemindMe",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login with Google button
        Button(
            onClick = { navController.navigate(Screen.Alarms.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.image1),
                    contentDescription = "Google Logo"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.login_with_google))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login with Apple ID button
        Button(
            onClick = {setSnackBarState(!snackbarVisibleState)},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.image2),
                    contentDescription = "Apple Logo"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.login_with_apple_id))
            }
        }

        if (snackbarVisibleState) {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) { Text(text = "Hubo un error con el proveedor.\nIntenta de nuevo.") }
        }
    }
}