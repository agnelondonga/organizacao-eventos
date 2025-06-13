package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.app.TimePickerDialog
import kotlinx.coroutines.launch

@Composable
fun ScheduleDayScreen(locationName: String, navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(10, 0)) }
    var notificationEnabled by remember { mutableStateOf(true) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "Agendar Dia",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Box {
                    var menuExpanded by remember { mutableStateOf(false) }
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
                            text = { Text("Ajuda") },
                            onClick = { /* TODO: Implementar ajuda */ }
                        )
                    }
                }
            }

            // Calendário
            Card(
                modifier = Modifier
                    .width(380.dp)
                    .height(380.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                CalendarView(
                    selectedDate = selectedDate,
                    onDateSelected = { date -> selectedDate = date }
                )
            }

            // Campo de hora
            Card(
                modifier = Modifier
                    .width(380.dp)
                    .height(80.dp)
                    .padding(horizontal = 16.dp),
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
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Hora",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "Hora",
                        color = Color(0xFF585858),
                        fontSize = 20.sp
                    )
                    TextButton(onClick = {
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                selectedTime = LocalTime.of(hour, minute)
                            },
                            selectedTime.hour,
                            selectedTime.minute,
                            true
                        )
                        timePickerDialog.show()
                    }) {
                        Text(
                            text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                            color = Color(0xFF585858),
                            fontSize = 20.sp
                        )
                    }
                }
            }

            // Campo de notificação
            Card(
                modifier = Modifier
                    .width(380.dp)
                    .height(80.dp)
                    .padding(horizontal = 16.dp),
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
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificação",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "Notificação",
                        color = Color(0xFF585858),
                        fontSize = 20.sp
                    )
                    TextButton(onClick = { notificationEnabled = !notificationEnabled }) {
                        Text(
                            text = if (notificationEnabled) "Sim" else "Não",
                            color = Color(0xFF585858),
                            fontSize = 20.sp
                        )
                    }
                }
            }

            // Botão Confirmar Dia
            Button(
                onClick = { showConfirmationDialog = true },
                modifier = Modifier
                    .width(380.dp)
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Confirmar Dia", color = White, fontSize = 16.sp)
            }
        }

        // Diálogo de confirmação
        if (showConfirmationDialog) {
            Dialog(onDismissRequest = { showConfirmationDialog = false }) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Confirmação do Evento",
                            color = Green,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Data: ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}\n" +
                                    "Hora: ${selectedTime.format(DateTimeFormatter.ofPattern("HH:mm"))}\n" +
                                    "Local: $locationName\n" +
                                    "Agendamento confirmado para conhecer o local.",
                            color = Black,
                            fontSize = 16.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Button(
                            onClick = {
                                showConfirmationDialog = false
                                navController.navigate("event_details/Outros")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Obrigado", color = White, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarView(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val pagerState = rememberPagerState(initialPage = 120) // Começa no mês atual (120 meses após o início)
    val months = (-120..120).map { LocalDate.now().plusMonths(it.toLong()) } // 240 meses (~20 anos)
    val coroutineScope = rememberCoroutineScope()

    // Atualiza o título com base na página atual
    val currentMonth = months[pagerState.currentPage]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowLeft,
                    contentDescription = "Mês Anterior",
                    tint = Green
                )
            }
            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")).replaceFirstChar { it.uppercase() },
                color = Green,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = "Próximo Mês",
                    tint = Green
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalPager(
            count = months.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val month = months[page]
            CalendarMonth(
                month = month,
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
}

@Composable
fun CalendarMonth(month: LocalDate, selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val firstDayOfMonth = month.withDayOfMonth(1)
    val daysInMonth = month.lengthOfMonth()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Domingo = 0, Segunda = 1, etc.

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb").forEach { day ->
                Text(
                    text = day,
                    color = Black,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        var day = 1
        for (week in 0..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    if (week == 0 && dayOfWeek < firstDayOfWeek || day > daysInMonth) {
                        Text(
                            text = "",
                            modifier = Modifier
                                .weight(1f)
                                .size(40.dp)
                        )
                    } else {
                        val currentDate = firstDayOfMonth.plusDays((day - 1).toLong())
                        val isSelected = currentDate == selectedDate
                        TextButton(
                            onClick = { onDateSelected(currentDate) },
                            modifier = Modifier
                                .weight(1f)
                                .size(40.dp),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = if (isSelected) Green else Color.Transparent,
                                contentColor = if (isSelected) White else Black
                            )
                        ) {
                            Text(
                                text = day.toString(),
                                fontSize = 16.sp
                            )
                        }
                        day++
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleDayScreenPreview() {
    EventosAppTheme {
        ScheduleDayScreen(locationName = "HCCT", navController = rememberNavController())
    }
}