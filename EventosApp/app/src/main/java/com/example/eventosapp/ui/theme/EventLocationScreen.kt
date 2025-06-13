package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventosapp.R

@Composable
fun EventLocationScreen(navController: NavController) {
    val locations = listOf(
        Location("HCCT", R.drawable.ic_location1),
        Location("Hotel Epic Sana", R.drawable.ic_location2),
        Location("CCB", R.drawable.ic_location3),
        Location("Hotel Diamante", R.drawable.ic_location4),
        Location("Clube S", R.drawable.ic_location5),
        Location("Clube Naval", R.drawable.ic_location6),
        Location("Mussulo", R.drawable.ic_location7),
        Location("Ayana Resort", R.drawable.ic_location8),
        Location("Del Mar", R.drawable.ic_location9),
        Location("Local 10", R.drawable.ic_location10)
    )

    var searchQuery by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
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
                    text = "Local do Evento",
                    color = Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
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

            // Barra de pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar direção") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Coração",
                        tint = Green
                    )
                },
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Lista de locais
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(locations.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }) { location ->
                    LocationCard(location = location, navController = navController)
                }
            }
        }
    }
}

data class Location(val name: String, val imageRes: Int)

@Composable
fun LocationCard(location: Location, navController: NavController) {
    var isFavorite by remember { mutableStateOf(false) }
    val favoriteColor = Color(0xFFE91E63) // Vermelho vibrante que combina com o tema

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("location_details/${location.name}") },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = location.imageRes),
                contentDescription = "${location.name} Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = location.name,
                    color = Green,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Coração",
                        tint = favoriteColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventLocationScreenPreview() {
    EventosAppTheme {
        EventLocationScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    EventosAppTheme {
        LocationCard(location = Location("HCCT", R.drawable.ic_location1), navController = rememberNavController())
    }
}