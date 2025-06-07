package com.example.dubnikinfo.presentation.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dubnikinfo.R
import com.example.dubnikinfo.presentation.ui.navigation.Screen
import com.example.dubnikinfo.utils.formatLocalDateToString

@Composable
fun HomeScreen (
    onCardClick: () -> Unit,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val temperature by homeViewModel.temperature.collectAsState()
    val trashMap by homeViewModel.firstPickups.collectAsState()
    val firstDate = trashMap.keys.firstOrNull()
    var dateText = stringResource(R.string.no_pickups)
    if (firstDate != null) {
        dateText = formatLocalDateToString(firstDate)
    }
    val trashType = trashMap[firstDate]
    Scaffold(
        content = { innerPadding ->
            val context = LocalContext.current
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box() {
                    Image(
                        painter = painterResource(id = R.drawable.mun_logo),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Card() {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box() {
                            Text (
                                text = "$temperature°C",
                                fontSize = 40.sp,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.Trash.route)
                        },
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column() {
                            trashType?.forEach { type ->
                                Image(
                                    painter = painterResource(type.id),
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                        }
                        Column (
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            Text(
                                text = stringResource(R.string.closest_pickup),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                                )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = dateText,
                                fontSize = 22.sp,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                HomeScreenCard(
                    R.drawable.news_icon,
                    stringResource(R.string.actualities),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Actualities.route)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                HomeScreenCard(
                    R.drawable.map_icon,
                    stringResource(R.string.places),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Places.route)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                HomeScreenCard(R.drawable.report_icon, stringResource(R.string.reports))
            }
        }
    )
}

@Composable
fun HomeScreenCard(
    @DrawableRes icon: Int,
    name: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Text(
                text = name,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}