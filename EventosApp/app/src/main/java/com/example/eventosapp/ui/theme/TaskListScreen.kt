package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun TaskListScreen(navController: NavController, viewModel: AppViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp) // Alinhado com EventCreationScreen
                .padding(bottom = 80.dp)
        ) {
            // Barra superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .padding(bottom = 8.dp), // Alinhado com EventCreationScreen
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Green,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Lista de Tarefas",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.size(32.dp)) // Espaço para alinhamento
            }

            // Conteúdo com rolagem
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp), // Alinhado com EventCreationScreen
                verticalArrangement = Arrangement.spacedBy(12.dp), // Alinhado com EventCreationScreen
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(tasks) { task ->
                    TaskCard(task = task, onMenuClick = { action ->
                        when (action) {
                            "details" -> navController.navigate("task_details/${task.name}/${task.status}/${task.icon}")
                            "edit" -> navController.navigate("task_edit/${task.name}/${task.status}/${task.icon}")
                            "delete" -> viewModel.deleteTask(task)
                        }
                    })
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("task_edit") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp), // Alinhado com EventCreationScreen
            containerColor = Green,
            contentColor = White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Tarefa",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun TaskCard(task: Task, onMenuClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (task.name) {
                        "Localização" -> Icons.Default.Place
                        "Lista de Convidados" -> Icons.Default.Group
                        "Comida" -> Icons.Default.LocalDining
                        "Presentes" -> Icons.Default.Redeem
                        "Fotógrafo" -> Icons.Default.PhotoCamera
                        "Decoração" -> Icons.Default.Info // Ícone genérico para nova tarefa
                        else -> Icons.Default.Info
                    },
                    contentDescription = "${task.name} Icon",
                    modifier = Modifier.size(32.dp),
                    tint = Green
                )
                Spacer(modifier = Modifier.width(40.dp)) // Espaço entre ícone e texto
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = task.name,
                        color = Green,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(
                                    color = when (task.status) {
                                        TaskStatus.DONE -> Color(0xFF10AF60) // Verde
                                        TaskStatus.IN_PROGRESS -> Color(0xFF2978D3) // Azul
                                        TaskStatus.PAUSED -> Color(0xFFF7CA54) // Amarelo
                                        TaskStatus.NOT_STARTED -> Color(0xFF261F59) // Azul escuro
                                    },
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (task.status) {
                                TaskStatus.DONE -> "Feito"
                                TaskStatus.IN_PROGRESS -> "Em Progresso"
                                TaskStatus.PAUSED -> "Em Pausa"
                                TaskStatus.NOT_STARTED -> "Sem Começar"
                            },
                            color = Green,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu de ações",
                        tint = Green,
                        modifier = Modifier.size(32.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ver Detalhes") },
                        onClick = {
                            onMenuClick("details")
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            onMenuClick("edit")
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Apagar") },
                        onClick = {
                            onMenuClick("delete")
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    EventosAppTheme {
        TaskListScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    EventosAppTheme {
        TaskCard(
            task = Task("Localização", TaskStatus.DONE, R.drawable.ic_meeting, ""),
            onMenuClick = {}
        )
    }
}