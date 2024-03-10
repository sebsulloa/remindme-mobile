package com.misw.remindme.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.misw.remindme.R
import com.misw.remindme.ui.navigation.Screen

@Composable
fun RemindMeBottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        val icons = listOf(Screen.Calendar.icon, Screen.Alarms.icon, Screen.User.icon)
        val routes = listOf(Screen.Calendar.route, Screen.Alarms.route, Screen.User.route)

        icons.forEachIndexed { index, icon ->
            val isSelected = routes[index] == currentRoute
            val tint =
                if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(routes[index]) },
                icon = {
                    Icon(icon, contentDescription = routes[index], tint = tint)
                },
                label = {
                    Text(
                        text = stringResource(id = getLabelResourceForRoute(routes[index])),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

@StringRes
fun getLabelResourceForRoute(route: String): Int {
    return when (route) {
        Screen.Alarms.route -> Screen.Alarms.resourceId
        Screen.Calendar.route -> Screen.Calendar.resourceId
        Screen.User.route -> Screen.User.resourceId
        else -> R.string.app_name
    }
}