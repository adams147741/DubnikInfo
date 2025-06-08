package com.example.dubnikinfo.presentation.ui.garbageCollection

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.local.trash.TrashType
import com.example.dubnikinfo.presentation.ui.TopBar.TopBar
import com.example.dubnikinfo.utils.formatLocalDateToString
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun GarbageCollectionScreen(
    viewModel: GarbageViewModel,
    onDayClick: (LocalDate, List<TrashType>) -> Unit,
    onBackClick: () -> Unit
) {
    val pickups by viewModel.pickups.collectAsState()
    Scaffold (
        topBar = {
            TopBar(
                title = stringResource(R.string.trash_collection),
                onNavigationClick = onBackClick,
            )
        },
        content = { innerPadding ->
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
            var selectedTypes by remember { mutableStateOf<List<TrashType>>(emptyList()) }
            Box(Modifier.padding(innerPadding)) {
                TrashCalendar(
                    events = pickups,
                    onDayClick = { date, types ->
                        selectedDate = date
                        selectedTypes = types
                    }
                )
                if (selectedDate != null && selectedTypes.isNotEmpty()) {
                    TrashTypeDialog(
                        date = selectedDate!!,
                        types = selectedTypes,
                        onDismiss = {
                            selectedDate = null
                            selectedTypes = emptyList()
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun TrashCalendar(
    events: Map<LocalDate, List<TrashType>>,
    onDayClick: (LocalDate, List<TrashType>) -> Unit,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    Column () {
        val daysOfWeek = daysOfWeek()
        DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        VerticalCalendar(
            state = state,
            dayContent = { day ->
                val types = events[day.date].orEmpty()
                Day(
                    day = day,
                    typeList = types,
                    isToday = day.date == LocalDate.now(),
                    onDayClick = onDayClick
                )
            },
            monthHeader = { month ->
                MonthHeader(month.yearMonth)
            }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isToday: Boolean,
    typeList: List<TrashType>,
    onDayClick: (LocalDate, List<TrashType>) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) Color.Red else Color.Gray,
            ),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onDayClick(day.date, typeList) }
        )
        {
            Text(text = day.date.dayOfMonth.toString())
            if (typeList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Row (
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    typeList.forEach { type ->
                        Image(
                            painter = painterResource(type.id),
                            contentDescription = null,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MonthHeader(yearMonth: YearMonth) {
    Text(
        text = yearMonth.month.name.lowercase().replaceFirstChar { it.uppercase() } + " ${yearMonth.year}",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.name.take(3),
            )
        }
    }
}

@Composable
fun TrashTypeDialog(
    date: LocalDate,
    types: List<TrashType>,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.trash_collection) + " " + formatLocalDateToString(date)) },
        text = {
            Column {
                Text(
                    text = stringResource(R.string.trash_types),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                types.forEach { type ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(type.id),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = stringResource(type.title),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.ok),
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp)
            )
        }
    )
}

@Composable
@Preview
fun GarbageCollectionScreenPreview() {
    val samplePickups = mapOf(
        LocalDate.of(2025, 5, 6) to listOf(TrashType.MUNICIPAL),
        LocalDate.of(2025, 5, 9) to listOf(TrashType.MUNICIPAL)
    )
    TrashCalendar(
        events = samplePickups,
        onDayClick = { _, _ -> }
    )
}