package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun LocationDetailsScreen(locationName: String, navController: NavController) {
    val location = when (locationName) {
        "HCCT" -> Location("HCCT", R.drawable.ic_location1)
        "Hotel Epic Sana" -> Location("Hotel Epic Sana", R.drawable.ic_location2)
        "CCB" -> Location("CCB", R.drawable.ic_location3)
        "Hotel Diamante" -> Location("Hotel Diamante", R.drawable.ic_location4)
        "Clube S" -> Location("Clube S", R.drawable.ic_location5)
        "Clube Naval" -> Location("Clube Naval", R.drawable.ic_location6)
        "Mussulo" -> Location("Mussulo", R.drawable.ic_location7)
        "Ayana Resort" -> Location("Ayana Resort", R.drawable.ic_location8)
        "Del Mar" -> Location("Del Mar", R.drawable.ic_location9)
        "Local 10" -> Location("Local 10", R.drawable.ic_location10)
        else -> Location("HCCT", R.drawable.ic_location1) // Fallback
    }

    var searchQuery by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }
    var isStarFavorite by remember { mutableStateOf(false) } // Estado para a primeira estrela

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
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
                        text = "Detalhes do Local",
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
                                text = { Text("Filtrar") },
                                onClick = { /* TODO: Implementar filtro */ }
                            )
                            DropdownMenuItem(
                                text = { Text("Ordenar") },
                                onClick = { /* TODO: Implementar ordenação */ }
                            )
                        }
                    }
                }
            }

            item {
                // Detalhes do local
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = location.imageRes),
                            contentDescription = "${location.name} Image",
                            modifier = Modifier
                                .width(325.dp)
                                .height(330.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .align(Alignment.CenterHorizontally)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = location.name,
                                color = Green,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { isStarFavorite = !isStarFavorite },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isStarFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "Rating",
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = "4.82",
                                    color = Black,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                        Text(
                            text = "Descrição: Um espaço elegante e versátil, perfeito para eventos de todos os tamanhos. Com instalações modernas e uma localização privilegiada, oferece conforto e sofisticação para casamentos, conferências e celebrações.",
                            color = Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }

            item {
                // Avaliações
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "4.82",
                                color = Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Text(
                                text = "Avaliações",
                                color = Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        ReviewCard(
                            reviewerName = "Irina Abílio",
                            profileImageRes = R.drawable.ic_irina,
                            reviewText = "Excelente localização, com instalações modernas e equipe muito atenciosa. Perfeito para o nosso evento corporativo!"
                        )
                        ReviewCard(
                            reviewerName = "Fernando Maria",
                            profileImageRes = R.drawable.ic_maria,
                            reviewText = "Um espaço incrível para casamentos. A decoração e o serviço foram impecáveis, superou nossas expectativas."
                        )
                    }
                }
            }

            item {
                // Botão Confirmar Dia
                Button(
                    onClick = { navController.navigate("schedule_day/$locationName") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Confirmar Dia", color = White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun ReviewCard(reviewerName: String, profileImageRes: Int, reviewText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = profileImageRes),
            contentDescription = "$reviewerName Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = reviewerName,
                color = Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = reviewText,
                color = Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsScreenPreview() {
    EventosAppTheme {
        LocationDetailsScreen(locationName = "HCCT", navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewCardPreview() {
    EventosAppTheme {
        ReviewCard(
            reviewerName = "Irina Abílio",
            profileImageRes = R.drawable.ic_irina,
            reviewText = "Excelente localização, com instalações modernas e equipe muito atenciosa."
        )
    }
}