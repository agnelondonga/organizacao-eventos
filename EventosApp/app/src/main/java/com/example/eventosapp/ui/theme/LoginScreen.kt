package com.example.eventosapp.ui.theme

import androidx.compose.animation.AnimatedVisibility
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

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var showEmailError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }

    fun validateLogin(): Boolean {
        showEmailError = email.isBlank()
        showPasswordError = password.length < 6
        return !showEmailError && !showPasswordError
    }

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
                Text(
                    text = "BEM-VINDO!",
                    color = White,
                    fontSize = 32.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
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
                        Text(
                            text = "Login",
                            fontSize = 28.sp,
                            color = Green
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                showEmailError = false
                            },
                            isError = showEmailError,
                            placeholder = { Text("Email ou Número") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Green,
                                unfocusedBorderColor = Color.LightGray,
                                errorBorderColor = Color.Red,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )
                        AnimatedVisibility(visible = showEmailError) {
                            Text("Campo obrigatório", color = Color.Red, fontSize = 14.sp)
                        }

                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                showPasswordError = false
                            },
                            isError = showPasswordError,
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
                                errorBorderColor = Color.Red,
                                focusedContainerColor = Color(0xFFF8F9FA),
                                unfocusedContainerColor = Color(0xFFF8F9FA)
                            )
                        )
                        AnimatedVisibility(visible = showPasswordError) {
                            Text("Senha deve ter pelo menos 6 caracteres", color = Color.Red, fontSize = 14.sp)
                        }

                        Text(
                            text = "Forgot password?",
                            color = Green,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { }
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        Button(
                            onClick = {
                                if (validateLogin()) {
                                    // Aqui conecta com API futuramente
                                    if (email == "teste@email.com" && password == "123456") {
                                        navController.navigate("event_selection")
                                    } else {
                                        loginError = "Email ou senha incorretos"
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Sign In", color = White, fontSize = 20.sp)
                        }

                        AnimatedVisibility(visible = loginError.isNotEmpty()) {
                            Text(loginError, color = Color.Red, fontSize = 14.sp)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Don't have an account?",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Sign up",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = Green,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                navController.navigate("create_account")
                            }
                        )
                    }
                }
            }
        }
    }
}
