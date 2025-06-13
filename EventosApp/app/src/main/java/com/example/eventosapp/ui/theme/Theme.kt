package com.example.eventosapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun EventosAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Green,
            onPrimary = White,
            secondary = CardBackground,
            onSecondary = Black,
            tertiary = White,
            onTertiary = Black,
            background = Black,
            onBackground = White,
            surface = CardBackground,
            onSurface = Black
        )
    } else {
        lightColorScheme(
            primary = Green,
            onPrimary = White,
            secondary = CardBackground,
            onSecondary = Black,
            tertiary = White,
            onTertiary = Black,
            background = BackgroundGray,
            onBackground = Black,
            surface = White,
            onSurface = Black
        )
    }

    // Configurar a cor da barra de status
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}