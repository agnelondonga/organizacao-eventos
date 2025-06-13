package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun EventSelectionScreen(navController: NavController, viewModel: AppViewModel = viewModel()) {
    val events by viewModel.events.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Seleção de Eventos",
                color = Green,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .padding(top = 50.dp, bottom = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .width(360.dp)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(events) { event ->
                        EventCard(event = event) {
                            if (event.name == "Outros") {
                                navController.navigate("event_creation")
                            } else {
                                navController.navigate("custom_event_creation/${event.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(
                    id = when (event.name) {
                        "Casamento" -> R.drawable.ic_wedding
                        "Aniversário" -> R.drawable.ic_birthday
                        "Batismo" -> R.drawable.ic_baptism
                        "Reunião de Empresas" -> R.drawable.ic_meeting
                        "Graduação" -> R.drawable.ic_graduation
                        "Outros" -> R.drawable.ic_other
                        else -> R.drawable.ic_other
                    }
                ),
                contentDescription = "${event.name} Icon",
                modifier = Modifier
                    .size(width = 123.dp, height = 76.dp)
                    .padding(end = 16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.name,
                    color = Green,
                    fontSize = 20.3.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventSelectionScreenPreview() {
    EventosAppTheme {
        EventSelectionScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    EventosAppTheme {
        EventCard(
            event = Event(
                name = "Casamento",
                attendees = 100,
                date = "2025-05-10",
                time = "18:00",
                location = "Igreja São Paulo",
                menu = "Jantar",
                estimatedCost = "5000"
            ),
            onClick = {}
        )
    }
}