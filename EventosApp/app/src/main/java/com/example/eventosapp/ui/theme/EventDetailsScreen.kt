package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun EventDetailsScreen(eventName: String, navController: NavController, viewModel: AppViewModel = viewModel()) {
    val events by viewModel.events.collectAsState()
    val event = events.find { it.name == eventName } ?: Event(
        name = eventName,
        attendees = 0,
        date = "",
        time = "",
        location = "",
        menu = "",
        estimatedCost = ""
    )
    val menu = viewModel.getMenuForEvent(eventName)

    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Barra superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Green
                    )
                }
                Text(
                    text = "Detalhes do Evento",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = Green
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver Tarefas") },
                            onClick = { navController.navigate("task_list") }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = { /* TODO: Implementar ajuda */ }
                        )
                    }
                }
            }

            // Conteúdo do evento
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id = when (eventName) {
                                "Casamento" -> R.drawable.ic_wedding
                                "Aniversário" -> R.drawable.ic_birthday
                                "Batismo" -> R.drawable.ic_baptism
                                "Reunião de Empresas" -> R.drawable.ic_meeting
                                "Graduação" -> R.drawable.ic_graduation
                                "Outros" -> R.drawable.ic_other
                                else -> R.drawable.ic_other
                            }
                        ),
                        contentDescription = "Imagem do Evento",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Text(
                        text = eventName,
                        color = Green,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Número de Pessoas: ${event.attendees}",
                        color = Black,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Data: ${event.date}",
                        color = Black,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Hora: ${event.time}",
                        color = Black,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Local: ${event.location}",
                        color = Black,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Ementa:",
                        color = Green,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (menu != null) {
                            "• Aperitivos: ${menu.appetizers}\n" +
                                    "• Prato Principal: ${menu.mainCourse}\n" +
                                    "• Sobremesa: ${menu.dessert}\n" +
                                    "• Bebidas: ${menu.drinks}"
                        } else {
                            "Nenhuma ementa configurada"
                        },
                        color = Black,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Custo Estimado: ${event.estimatedCost}",
                        color = Black,
                        fontSize = 16.sp
                    )

                    // Botão para EventSelectionScreen
                    Button(
                        onClick = { navController.navigate("event_selection") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Voltar aos Eventos", color = White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailsScreenPreview() {
    EventosAppTheme {
        EventDetailsScreen(eventName = "Casamento", navController = rememberNavController())
    }
}