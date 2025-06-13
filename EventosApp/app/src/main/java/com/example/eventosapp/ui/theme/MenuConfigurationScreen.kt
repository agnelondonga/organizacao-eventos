package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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

@Composable
fun MenuConfigurationScreen(
    eventName: String,
    navController: NavController,
    viewModel: AppViewModel = viewModel()
) {
    var name by remember { mutableStateOf("Ementa 1") }
    var appetizers by remember { mutableStateOf("") }
    var mainCourse by remember { mutableStateOf("") }
    var dessert by remember { mutableStateOf("") }
    var drinks by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val menuOptions = listOf("Ementa 1", "Ementa 2", "Ementa 3")

    // Carregar ementa existente, se houver
    val existingMenu = viewModel.getMenuForEvent(eventName)
    LaunchedEffect(existingMenu) {
        if (existingMenu != null) {
            name = existingMenu.name
            appetizers = existingMenu.appetizers
            mainCourse = existingMenu.mainCourse
            dessert = existingMenu.dessert
            drinks = existingMenu.drinks
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
                    text = "Configurar Ementa para $eventName",
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
                            text = { Text("Limpar Formulário") },
                            onClick = {
                                name = "Ementa 1"
                                appetizers = ""
                                mainCourse = ""
                                dessert = ""
                                drinks = ""
                                menuExpanded = false
                            }
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
                            // Seleção do nome da ementa
                            Box {
                                OutlinedTextField(
                                    value = name,
                                    onValueChange = {},
                                    label = { Text("Nome da Ementa") },
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
                                                name = option
                                                dropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = appetizers,
                                onValueChange = { appetizers = it },
                                label = { Text("Aperitivos") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = mainCourse,
                                onValueChange = { mainCourse = it },
                                label = { Text("Prato Principal") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = dessert,
                                onValueChange = { dessert = it },
                                label = { Text("Sobremesa") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = drinks,
                                onValueChange = { drinks = it },
                                label = { Text("Bebidas") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Botões Cancelar (esquerda) e Salvar (direita)
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
                                        if (appetizers.isNotBlank() && mainCourse.isNotBlank()) {
                                            val newMenu = Menu(
                                                eventName = eventName,
                                                name = name,
                                                appetizers = appetizers,
                                                mainCourse = mainCourse,
                                                dessert = dessert,
                                                drinks = drinks
                                            )
                                            if (existingMenu == null) {
                                                viewModel.addMenu(newMenu)
                                            } else {
                                                viewModel.updateMenu(existingMenu, newMenu)
                                            }
                                            // Atualizar o evento com o nome da ementa
                                            val currentEvent = viewModel.events.value.find { it.name == eventName }
                                            if (currentEvent != null) {
                                                viewModel.updateEvent(
                                                    currentEvent,
                                                    currentEvent.copy(menu = name)
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuConfigurationScreenPreview() {
    EventosAppTheme {
        MenuConfigurationScreen(eventName = "Casamento", navController = rememberNavController())
    }
}