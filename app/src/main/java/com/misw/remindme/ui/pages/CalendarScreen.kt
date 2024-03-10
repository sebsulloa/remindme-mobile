package com.misw.remindme.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.misw.remindme.R
import com.misw.remindme.ui.composables.RemindMeBottomBar
import com.misw.remindme.ui.composables.RemindMeTopBar
import com.misw.remindme.ui.navigation.Screen
import com.misw.remindme.utils.AlarmParser
import com.misw.remindme.utils.MonthHeader
import com.misw.remindme.utils.SimpleCalendarTitle
import com.misw.remindme.utils.rememberFirstMostVisibleMonth
import kotlinx.coroutines.launch
import java.time.YearMonth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavController) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(50) }
    val endMonth = remember { currentMonth.plusMonths(50) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }
    val context = LocalContext.current
    val alarmsList = remember {
        AlarmParser.parseJson(context, R.raw.alarms)
    }

    Scaffold(
        bottomBar = { RemindMeBottomBar(navController) },
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
                val state = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = daysOfWeek.first(),
                )
                val coroutineScope = rememberCoroutineScope()
                val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

                RemindMeTopBar(title = "RemindMe")
                SimpleCalendarTitle(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                    currentMonth = visibleMonth.yearMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                        }
                    },
                )
                HorizontalCalendar(
                    modifier = Modifier.testTag("Calendar"),
                    state = state,
                    dayContent = { day ->
                        Day(day, isSelected = selections.contains(day)) { clicked ->
                            if (selections.contains(clicked)) {
                                selections.remove(clicked)
                            } else {
                                selections.add(clicked)
                            }
                        }
                    },
                    monthHeader = {
                        MonthHeader(daysOfWeek = daysOfWeek)
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
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> if (isSelected) Color.White else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> MaterialTheme.colorScheme.onSurface
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}