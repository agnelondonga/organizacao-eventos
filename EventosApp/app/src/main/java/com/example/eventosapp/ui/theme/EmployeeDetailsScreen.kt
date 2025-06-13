package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun EmployeeDetailsScreen(navController: NavController, employee: Employee) {
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
                    text = "Detalhes do Empregado",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = employee.icon),
                        contentDescription = "${employee.name} Icon",
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = employee.name,
                        color = Green,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "Função: ${employee.role}",
                    color = Black,
                    fontSize = 16.sp
                )

                Text(
                    text = "Status: ${if (employee.isAvailable) "Disponível" else "Indisponível"}",
                    color = if (employee.isAvailable) StatusDone else StatusInProgress,
                    fontSize = 16.sp
                )

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Voltar", color = White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeDetailsScreenPreview() {
    EventosAppTheme {
        EmployeeDetailsScreen(
            navController = rememberNavController(),
            employee = Employee("João Silva", "Garçom", true, R.drawable.ic_other)
        )
    }
}
