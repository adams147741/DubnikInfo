package com.example.dubnikinfo.presentation.ui.actualities

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.local.NewsLine
import com.example.dubnikinfo.presentation.ui.TopBar.TopBar
import androidx.core.net.toUri

@Composable
fun ActualitiesScreen(
    news:List<NewsLine>,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current;
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.actualities), onNavigationClick = onBackClick)
        },
        content = { innerPadding ->
            Column (modifier = Modifier.padding(innerPadding)) {
                if (news.isEmpty()) {
                Text(
                    text = stringResource(R.string.No_actualities),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
                LazyColumn(
                ) {
                    items(news) { item ->
                        NewsCard(
                            headline = item.title,
                            date = item.date,
                            text = item.description,
                            link = item.link,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, item.link.toUri())
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun NewsCard(
    headline: String,
    date: String,
    text: String,
    link: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = headline,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = date,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = text,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
fun ActualitiesPreview() {
    val fakeData = listOf(
        NewsLine("Veľkonočné prianie obce pre všetkých občanov", "16.04.2025", "Obecný úrad praje všetkým občanom požehnané a radostné veľkonočné sviatky. Nech je táto jar plná sily, zdravia a pohody v kruhu najbližších. Starosta, poslanci obecného zastupiteľstva a zamestnanci obecného úradu sa pripájajú k prianiu.", "google.com"),
        NewsLine("Obecný úrad v Dubníku bude dňa 17.04.2025 ZATVORENÝ", "15.04.2025", "Oznamujeme občanom, že dňa 17 apríla 2025 – vo štvrtok bude obecný úrad v Dubníku z organizačno-technických dôvodov zatvorený. Tájékoztatjuk a lakosságot, hogy 2025. április 17-én - csütörtökön a Csúzi községi hivatal szervezési okokból zárva lesz.", "google.com"),
        NewsLine("Veľkonočné trhy v Dubníku s kultúrnym programom", "08.04.2025", "Základná škola s materskou školou v Dubníku a obec Dubník srdečne pozývajú na Veľkonočné trhy, ktoré sa uskutočnia 12. apríla 2025 od 13.00 do 16.00 hod. v budove školy. Pripravený je aj krátky kultúrny program. Tešíme sa na vašu účasť!", "google.com")
    )
    ActualitiesScreen(news = fakeData, onBackClick = {})
}

@Preview
@Composable
fun NewsCardPreview() {
    NewsCard(headline = "Novina", date = "24.4.2024", text = "Obecný úrad praje všetkým občanom požehnané a radostné veľkonočné sviatky. Nech je táto jar plná sily, zdravia a pohody v kruhu najbližších. Starosta, poslanci obecného zastupiteľstva a zamestnanci obecného úradu sa pripájajú k prianiu.", "google.com",modifier = Modifier)
}