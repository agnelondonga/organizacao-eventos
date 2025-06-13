package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeEditScreen(navController: NavController, employee: Employee?, viewModel: AppViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
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
                    text = if (employee == null) "Criar Empregado" else "Editar Empregado",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                var employeeName by remember { mutableStateOf(employee?.name ?: "") }
                OutlinedTextField(
                    value = employeeName,
                    onValueChange = { employeeName = it },
                    label = { Text("Nome do Empregado") },
                    modifier = Modifier.fillMaxWidth()
                )

                var role by remember { mutableStateOf(employee?.role ?: "") }
                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Função (ex.: Garçom, Fotógrafo)") },
                    modifier = Modifier.fillMaxWidth()
                )

                var isAvailable by remember { mutableStateOf(employee?.isAvailable ?: true) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Disponível", color = Green, fontSize = 16.sp)
                    Switch(
                        checked = isAvailable,
                        onCheckedChange = { isAvailable = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Green,
                            checkedTrackColor = White
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Green)
                    ) {
                        Text("Cancelar", color = White)
                    }
                    Button(
                        onClick = {
                            val newEmployee = Employee(
                                name = employeeName,
                                role = role,
                                isAvailable = isAvailable,
                                icon = employee?.icon ?: R.drawable.ic_other // Mantém o ícone ou usa um padrão
                            )
                            if (employee == null) {
                                viewModel.addEmployee(newEmployee)
                            } else {
                                viewModel.updateEmployee(employee, newEmployee)
                            }
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green)
                    ) {
                        Text("Salvar", color = White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeEditScreenPreview() {
    EventosAppTheme {
        EmployeeEditScreen(navController = rememberNavController(), employee = null)
    }
}