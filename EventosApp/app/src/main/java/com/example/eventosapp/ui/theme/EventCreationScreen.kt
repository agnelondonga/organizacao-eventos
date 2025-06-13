package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun EventCreationScreen(
    navController: NavController,
    eventName: String? = null,
    viewModel: AppViewModel = viewModel()
) {
    var name by remember { mutableStateOf(eventName ?: "") }
    var attendees by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedMenu by remember { mutableStateOf("Ementa 1") }
    var menuExpanded by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var searchDropdownExpanded by remember { mutableStateOf(false) }
    val employees by viewModel.employees.collectAsState()
    val events by viewModel.events.collectAsState()
    val menuOptions = listOf("Ementa 1", "Ementa 2", "Ementa 3")
    // Lista de funcionários adicionados ao evento
    val addedEmployees = events.find { it.name == name }?.employees ?: emptyList()
    // Filtrar funcionários com base na pesquisa (limitado a 2)
    val suggestedEmployees = employees
        .filter {
            searchQuery.isNotEmpty() &&
                    (it.name.contains(searchQuery, ignoreCase = true) || it.role.contains(searchQuery, ignoreCase = true))
        }
        .take(2) // Limitar a duas sugestões
    // Calcular custo estimado reativamente
    val estimatedCost by remember(attendees, selectedMenu) {
        derivedStateOf {
            viewModel.calculateEstimatedCost(attendees.toIntOrNull() ?: 0, selectedMenu)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            // Barra superior na área cinza
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
                    text = if (eventName == null) "Novo Evento" else "Editar Evento",
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
                            text = { Text("Salvar Rascunho") },
                            onClick = { menuExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Importar Dados") },
                            onClick = { menuExpanded = false }
                        )
                    }
                }
            }

            // Conteúdo com rolagem
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
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
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Nome do Evento") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = attendees,
                                onValueChange = { newValue ->
                                    if (newValue.all { it.isDigit() }) {
                                        attendees = newValue
                                    }
                                },
                                label = { Text("Número de Pessoas") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                            // Campo de descrição
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Descrição do Evento") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Campo de notas adicionais
                            OutlinedTextField(
                                value = notes,
                                onValueChange = { notes = it },
                                label = { Text("Notas Adicionais") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Seleção de ementa
                            Box {
                                OutlinedTextField(
                                    value = selectedMenu,
                                    onValueChange = {},
                                    label = { Text("Ementa") },
                                    modifier = Modifier.fillMaxWidth(),
                                    readOnly = true
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable { dropdownExpanded = true }
                                )
                                DropdownMenu(
                                    expanded = dropdownExpanded,
                                    onDismissRequest = { dropdownExpanded = false }
                                ) {
                                    menuOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                selectedMenu = option
                                                dropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Custo Estimado (somente leitura)
                            OutlinedTextField(
                                value = estimatedCost,
                                onValueChange = {},
                                label = { Text("Custo Estimado") },
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true
                            )

                            // Funcionários Adicionados com ícone "+"
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Funcionários Adicionados",
                                    color = Green,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                IconButton(onClick = { navController.navigate("employee_edit") }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Adicionar Novo Funcionário",
                                        tint = Green
                                    )
                                }
                            }
                            if (addedEmployees.isEmpty()) {
                                Text(
                                    text = "Nenhum funcionário adicionado",
                                    color = Black,
                                    fontSize = 14.sp
                                )
                            } else {
                                addedEmployees.forEach { employeeName ->
                                    val employee = employees.find { it.name == employeeName }
                                    if (employee != null) {
                                        var employeeMenuExpanded by remember { mutableStateOf(false) }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${employee.name} (${employee.role})",
                                                color = Black,
                                                fontSize = 16.sp
                                            )
                                            Row {
                                                IconButton(
                                                    onClick = {
                                                        viewModel.removeEmployeeFromEvent(name, employee.name)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "Remover",
                                                        tint = Color.Red
                                                    )
                                                }
                                                Box {
                                                    IconButton(onClick = { employeeMenuExpanded = true }) {
                                                        Icon(
                                                            imageVector = Icons.Default.MoreVert,
                                                            contentDescription = "Menu",
                                                            tint = Green
                                                        )
                                                    }
                                                    DropdownMenu(
                                                        expanded = employeeMenuExpanded,
                                                        onDismissRequest = { employeeMenuExpanded = false }
                                                    ) {
                                                        DropdownMenuItem(
                                                            text = { Text("Detalhes") },
                                                            onClick = {
                                                                navController.navigate("employee_details/${employee.name}")
                                                                employeeMenuExpanded = false
                                                            }
                                                        )
                                                        DropdownMenuItem(
                                                            text = { Text("Editar") },
                                                            onClick = {
                                                                navController.navigate("employee_edit/${employee.name}")
                                                                employeeMenuExpanded = false
                                                            }
                                                        )
                                                        DropdownMenuItem(
                                                            text = { Text("Apagar") },
                                                            onClick = {
                                                                // Não implementado
                                                                employeeMenuExpanded = false
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Barra de Pesquisa com Sugestões (limitado a 2 + "Ver mais")
                            Box {
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = {
                                        searchQuery = it
                                        searchDropdownExpanded = it.isNotEmpty()
                                    },
                                    label = { Text("Pesquisar Funcionários") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                DropdownMenu(
                                    expanded = searchDropdownExpanded,
                                    onDismissRequest = { searchDropdownExpanded = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    suggestedEmployees.forEach { employee ->
                                        DropdownMenuItem(
                                            text = { Text("${employee.name} (${employee.role})") },
                                            onClick = {
                                                viewModel.addEmployeeToEvent(name, employee.name)
                                                searchQuery = ""
                                                searchDropdownExpanded = false
                                            }
                                        )
                                    }
                                    if (searchQuery.isNotEmpty()) {
                                        DropdownMenuItem(
                                            text = { Text("Ver mais") },
                                            onClick = {
                                                navController.navigate("employee_management")
                                                searchDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Botões Cancelar (esquerda) e Próximo (direita)
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
                                            val newEvent = Event(
                                                name = name,
                                                attendees = attendees.toIntOrNull() ?: 0,
                                                date = "",
                                                time = "",
                                                location = "",
                                                menu = selectedMenu,
                                                estimatedCost = estimatedCost,
                                                employees = addedEmployees
                                            )
                                            if (eventName == null) {
                                                viewModel.addEvent(newEvent)
                                            } else {
                                                viewModel.updateEvent(
                                                    Event(
                                                        name = eventName,
                                                        attendees = 0,
                                                        date = "",
                                                        time = "",
                                                        location = "",
                                                        menu = "",
                                                        estimatedCost = "",
                                                        employees = emptyList()
                                                    ),
                                                    newEvent
                                                )
                                            }
                                            navController.navigate("event_location")
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Próximo", color = White, fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCreationScreenPreview() {
    EventosAppTheme {
        EventCreationScreen(navController = rememberNavController())
    }
}