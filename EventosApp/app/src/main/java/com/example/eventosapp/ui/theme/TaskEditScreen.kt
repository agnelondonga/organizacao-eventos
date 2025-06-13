package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun TaskEditScreen(
    taskName: String? = null,
    taskStatus: String? = null,
    taskIcon: String? = null,
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    var name by remember { mutableStateOf(taskName ?: "") }
    var description by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(taskStatus ?: TaskStatus.IN_PROGRESS.name) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .padding(top = 16.dp, bottom = 16.dp),
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
                Text(
                    text = if (taskName == null) "Nova Tarefa" else "Editar Tarefa",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = when (name) {
                        "Localização" -> Icons.Default.Place
                        "Lista de Convidados" -> Icons.Default.Group
                        "Comida" -> Icons.Default.LocalDining
                        "Presentes" -> Icons.Default.Redeem
                        "Fotógrafo" -> Icons.Default.PhotoCamera
                        "Decoração" -> Icons.Default.Info // Ícone genérico para Decoração
                        else -> Icons.Default.Info
                    },
                    contentDescription = "$name Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = Green
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome da Tarefa") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )

                var expanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = when (status) {
                            "DONE" -> "Feito"
                            "IN_PROGRESS" -> "Em Progresso"
                            "PAUSED" -> "Em Pausa"
                            "NOT_STARTED" -> "Sem Começar"
                            else -> "Em Progresso"
                        },
                        onValueChange = {},
                        label = { Text("Status") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { expanded = true }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Feito") },
                            onClick = {
                                status = TaskStatus.DONE.name
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Em Progresso") },
                            onClick = {
                                status = TaskStatus.IN_PROGRESS.name
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Em Pausa") },
                            onClick = {
                                status = TaskStatus.PAUSED.name
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sem Começar") },
                            onClick = {
                                status = TaskStatus.NOT_STARTED.name
                                expanded = false
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancelar", color = White, fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank()) {
                                val newTask = Task(
                                    name = name,
                                    status = TaskStatus.valueOf(status),
                                    icon = when (name) {
                                        "Localização" -> R.drawable.ic_meeting
                                        "Lista de Convidados" -> R.drawable.ic_other
                                        "Comida" -> R.drawable.ic_birthday
                                        "Presentes" -> R.drawable.ic_graduation
                                        "Fotógrafo" -> R.drawable.ic_baptism
                                        "Decoração" -> R.drawable.ic_wedding // Ícone específico para Decoração
                                        else -> R.drawable.ic_other
                                    },
                                    description = description
                                )
                                if (taskName == null) {
                                    viewModel.addTask(newTask)
                                } else {
                                    viewModel.updateTask(
                                        Task(
                                            name = taskName,
                                            status = TaskStatus.valueOf(taskStatus ?: "IN_PROGRESS"),
                                            icon = taskIcon?.toInt() ?: R.drawable.ic_other,
                                            description = ""
                                        ),
                                        newTask
                                    )
                                }
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Green),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Salvar", color = White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskEditScreenPreview() {
    EventosAppTheme {
        TaskEditScreen(navController = rememberNavController())
    }
}