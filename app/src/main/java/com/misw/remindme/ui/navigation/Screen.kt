package com.misw.remindme.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import com.misw.remindme.R

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Login : Screen("login", Icons.Filled.Lock, R.string.login)
    object Alarms : Screen("alarms", Icons.AutoMirrored.Filled.List, R.string.alarms)
    object Calendar : Screen("calendar", Icons.Filled.DateRange, R.string.calendar)
    object User : Screen("user", Icons.Filled.AccountCircle, R.string.user)
    object EditAlarm : Screen("edit", Icons.Filled.Edit, R.string.edit_alarm )
    object CreateAlarm : Screen("create", Icons.Filled.Add, R.string.create_alarm )
    object CreateAlarmVoice : Screen("create_voice", Icons.Filled.Add, R.string.create_alarm_voice )
}