package com.example.eventosapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eventosapp.R
import com.example.eventosapp.ui.theme.*

@Composable
fun CreateAccountScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.horizontalGradient(listOf(Green, Black)))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "CREATE YOUR",
                        color = White,
                        fontSize = 32.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Text(
                        text = "ACCOUNT",
                        color = White,
                        fontSize = 32.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = White,
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            placeholder = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )

                        OutlinedTextField(
                            value = contact,
                            onValueChange = { contact = it },
                            placeholder = { Text("Email ou Número") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Password") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (passwordVisible)
                                                R.drawable.ic_visibility
                                            else
                                                R.drawable.ic_visibility_off
                                        ),
                                        contentDescription = "Ver Senha",
                                        tint = Green
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            placeholder = { Text("Confirm Password") },
                            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (confirmPasswordVisible)
                                                R.drawable.ic_visibility
                                            else
                                                R.drawable.ic_visibility_off
                                        ),
                                        contentDescription = "Ver Senha",
                                        tint = Green
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navController.navigate("event_selection") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("SIGN UP", color = White, fontSize = 20.sp)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Have account already!",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Sign In",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Green,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                navController.navigate("login")
                            }
                        )
                    }
                }
            }
        }
    }
}
