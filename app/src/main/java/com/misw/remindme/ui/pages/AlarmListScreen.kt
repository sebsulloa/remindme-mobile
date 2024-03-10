package com.misw.remindme.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.misw.remindme.R
import com.misw.remindme.ui.composables.FabButtonItem
import com.misw.remindme.ui.composables.FabButtonMain
import com.misw.remindme.ui.composables.FabButtonSub
import com.misw.remindme.ui.composables.MultiFloatingActionButton
import com.misw.remindme.ui.composables.RemindMeBottomBar
import com.misw.remindme.ui.composables.RemindMeTopBar
import com.misw.remindme.ui.navigation.Screen
import com.misw.remindme.utils.AlarmItem
import com.misw.remindme.utils.AlarmParser
import com.misw.remindme.utils.Day
import com.misw.remindme.utils.getWeekPageTitle
import com.misw.remindme.utils.rememberFirstVisibleWeekAfterScroll
import java.time.LocalDate
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlarmsScreen(navController: NavController) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }
    var selection by remember { mutableStateOf(currentDate) }
    val context = LocalContext.current
    val alarmsList = remember {
        AlarmParser.parseJson(context, R.raw.alarms)
    }

    Scaffold(
        bottomBar = { RemindMeBottomBar(navController) },
        floatingActionButton = {
            MultiFloatingActionButton(
                items = listOf(
                    FabButtonItem(
                        iconRes = ImageVector.vectorResource(id = R.drawable.baseline_mic_24),
                        label = "Crear por voz",
                        route = Screen.CreateAlarmVoice.route
                    ),
                    FabButtonItem(
                        iconRes = Icons.Filled.Edit,
                        label = "Crear manual",
                        route = Screen.CreateAlarm.route
                    )
                ),
                onFabItemClicked = {
                    navController.navigate(it.route)
                },
                fabIcon = FabButtonMain(),
                fabOption = FabButtonSub()
            )
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF1F7F7))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val state = rememberWeekCalendarState(
                    startDate = startDate,
                    endDate = endDate,
                    firstVisibleWeekDate = currentDate,
                )
                val visibleWeek = rememberFirstVisibleWeekAfterScroll(state)
                RemindMeTopBar(title = "RemindMe")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF19a7a7))
                ) {
                    Text(
                        text = getWeekPageTitle(visibleWeek).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                WeekCalendar(
                    modifier = Modifier.background(color = Color(0xFF19a7a7)),
                    state = state,
                    dayContent = { day ->
                        Day(day.date, isSelected = selection == day.date) { clicked ->
                            if (selection != clicked) {
                                selection = clicked
                            }
                        }
                    },
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 43.dp, bottom = 100.dp)
                        .background(color = Color.White)
                ) {
                    items(alarmsList) { alarm ->
                        AlarmListItem(alarm = alarm,
                            onItemClick = {
                                navController.navigate(Screen.EditAlarm.route)
                            })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmListItem(alarm: AlarmItem, onItemClick: (AlarmItem) -> Unit) {
    val imageRes = when (alarm.category) {
        "Trabajo" -> R.drawable.briefcase_logo
        "Personal" -> R.drawable.house_logo
        "Recreacion" -> R.drawable.rec_logo
        else -> R.drawable.ic_launcher_background // Default image resource if category is not recognized
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClick(alarm) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Alarm Image",
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(text = alarm.name, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = alarm.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(end = 16.dp)
                )
                Text(
                    text = alarm.location,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 30.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}