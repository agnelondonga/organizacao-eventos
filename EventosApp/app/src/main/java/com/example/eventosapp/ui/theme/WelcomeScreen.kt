package com.example.eventosapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventosapp.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Green, Black), // Gradiente da esquerda (verde) para a direita (preto)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Ícone de evento e título "EVENTOS"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_event), // Ícone nativo do Android Studio
                    contentDescription = "App Icon",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "EVENTOS",
                    color = White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            // Texto de boas-vindas
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "WELCOME",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sign in or create an account to continue",
                    color = White.copy(alpha = 0.85f),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Botões grandes
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Sign In
                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp)
                        .border(3.dp, White, RoundedCornerShape(20.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(20.dp),
                    elevation = null
                ) {
                    Text(
                        text = "Sign In",
                        color = White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Sign Up
                Button(
                    onClick = { navController.navigate("create_account") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = White),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        color = Green,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
