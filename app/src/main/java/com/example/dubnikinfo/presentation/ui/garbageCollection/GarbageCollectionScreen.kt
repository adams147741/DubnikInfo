package com.example.dubnikinfo.presentation.ui.garbageCollection

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.TrashType
import com.example.dubnikinfo.presentation.ui.TopBar.TopBar
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
fun GarbageCollectionScreen(
    startMonth: YearMonth,
    endMonth: YearMonth,
    events: Map<LocalDate, List<TrashType>>,
    onDayClick: (LocalDate, List<TrashType>) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold (
        topBar = {
            TopBar(
                title = stringResource(R.string.trash_collection),
                onNavigationClick = onBackClick,
            )
        },
        content = { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                TrashCalendar(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    events = events,
                    onDayClick = onDayClick
                )
            }
        }
    )
}

@Composable
fun TrashCalendar(
    startMonth: YearMonth,
    endMonth: YearMonth,
    events: Map<LocalDate, List<TrashType>>,
    onDayClick: (LocalDate, List<TrashType>) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

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
                // If only using one TrashType per day, pick the first:
                val type = types.firstOrNull() ?: TrashType.NONE
                Day(
                    day = day,
                    type = type,
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
    type: TrashType = TrashType.NONE
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(text = day.date.dayOfMonth.toString())
            if (type != TrashType.NONE) {
                val image = painterResource(type.id)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
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
                text = dayOfWeek.name/*dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),*/
            )
        }
    }
}

@Composable
@Preview
fun GarbageCollectionScreenPreview() {
    val samplePickups = mapOf(
        LocalDate.of(2025, 5, 6) to listOf(TrashType.MUNICIPAL),
        LocalDate.of(2025, 5, 9) to listOf(TrashType.MUNICIPAL)
    )
    TrashCalendar(
        startMonth = YearMonth.of(2025, 5),
        endMonth = YearMonth.of(2025, 5),
        events = samplePickups,
        onDayClick = { _, _ -> }
    )
}