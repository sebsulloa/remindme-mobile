package com.misw.remindme.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.misw.remindme.ui.pages.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RemindMeNavigation() {
    val navController = rememberNavController()
    RemindMeNavHost(navController = navController)
}

@Composable
fun RemindMeNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Alarms.route) {
            AlarmsScreen(navController)
        }
        composable(route = Screen.User.route) {
            UserConfigScreen(navController)
        }
        composable(route = Screen.CreateAlarm.route) {
            CreateAlarmScreen(navController = navController)
        }
        composable(route= Screen.CreateAlarmVoice.route){
            VoiceAlarmScreen(navController = navController)
        }
        composable(route = Screen.EditAlarm.route) {
            EditAlarmScreen(navController = navController)
        }
        composable(route= Screen.Calendar.route){
            CalendarScreen(navController = navController)
        }
    }
}
