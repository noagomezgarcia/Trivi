package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Aquí asignamos nuestros colores personalizados a los roles de Material Design
private val LightColorScheme = lightColorScheme(
    primary = SelectorBlue,      // Azul Oscuro (se usará en botones por defecto y timer)
    onPrimary = Color.White,     // Texto blanco sobre azul oscuro
    background = AppBeige,       // EL FONDO BEIGE PARA TODA LA APP
    onBackground = AppTextBlack, // Texto negro sobre el fondo beige
    secondary = SharePurple,     // Lila para compartir
    onSecondary = Color.White,
    tertiary = ReturnPink,       // Rosa para volver
    onTertiary = Color.White
)

// ESTE ES EL TEMA QUE TE FALTABA
@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}