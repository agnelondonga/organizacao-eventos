package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun EmployeeManagementScreen(navController: NavController, viewModel: AppViewModel = viewModel()) {
    val employees by viewModel.employees.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp) // Espaço entre o topo e a barra superior
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
                    text = "Gerenciamento de Empregados",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Box {
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = Green
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.background(White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Adicionar Empregado") },
                            onClick = {
                                navController.navigate("employee_edit")
                                menuExpanded = false
                            }
                        )
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

            // Centralizar empregados verticalmente
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .width(360.dp) // Centralização horizontal
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(employees) { employee ->
                        EmployeeCard(employee = employee, onMenuClick = { action ->
                            when (action) {
                                "details" -> navController.navigate("employee_details/${employee.name}")
                                "edit" -> navController.navigate("employee_edit/${employee.name}")
                                "delete" -> viewModel.deleteEmployee(employee)
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeCard(employee: Employee, onMenuClick: (String) -> Unit) {
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
            // Ícone de perfil e informações do empregado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "${employee.name} Profile Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp),
                    tint = Green
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = employee.name,
                        color = Green,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = employee.role,
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Canvas(modifier = Modifier.size(10.dp)) {
                            drawCircle(
                                color = if (employee.isAvailable) Color(0xFF10AF60) else Color(0xFFFF6666),
                                radius = size.minDimension / 2
                            )
                        }
                    }
                }
            }

            // Ícone de três pontos com menu suspenso
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu de ações",
                        tint = Green
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(White)
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
                        text = { Text("Remover") },
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
fun EmployeeManagementScreenPreview() {
    EventosAppTheme {
        EmployeeManagementScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeCardPreview() {
    EventosAppTheme {
        EmployeeCard(
            employee = Employee("João Silva", "Garçom", true, R.drawable.ic_other),
            onMenuClick = {}
        )
    }
}