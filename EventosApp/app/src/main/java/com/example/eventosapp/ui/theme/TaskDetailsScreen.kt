package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun TaskDetailsScreen(
    taskName: String,
    taskStatus: String,
    taskIcon: String,
    navController: NavController
) {
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
                Icon(
                    imageVector = when (taskName) {
                        "Localização" -> Icons.Default.Place
                        "Lista de Convidados" -> Icons.Default.Group // Substituído People por Group
                        "Comida" -> Icons.Default.LocalDining // Substituído Restaurant por LocalDining
                        "Presentes" -> Icons.Default.Redeem // Substituído CardGiftcard por Redeem
                        "Fotógrafo" -> Icons.Default.PhotoCamera // Substituído CameraAlt por PhotoCamera
                        else -> Icons.Default.Info
                    },
                    contentDescription = "$taskName Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterHorizontally),
                    tint = Green
                )

                Text(
                    text = taskName,
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Status: ${
                        when (taskStatus) {
                            "DONE" -> "Feito"
                            "IN_PROGRESS" -> "Em Progresso"
                            "PAUSED" -> "Em Pausa"
                            else -> "Desconhecido"
                        }
                    }",
                    color = Black,
                    fontSize = 16.sp
                )

                Button(
                    onClick = { navController.navigate("task_edit/$taskName/$taskStatus/$taskIcon") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Editar Tarefa", color = White, fontSize = 16.sp)
                }

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
fun TaskDetailsScreenPreview() {
    EventosAppTheme {
        TaskDetailsScreen(
            taskName = "Localização",
            taskStatus = "DONE",
            taskIcon = R.drawable.ic_meeting.toString(),
            navController = rememberNavController()
        )
    }
}