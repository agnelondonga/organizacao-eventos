package com.example.eventosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventosapp.ui.theme.EventosAppTheme
import com.example.eventosapp.ui.theme.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventosAppTheme {
                Navigation()
            }
        }
    }
}
