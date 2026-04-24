package com.example.myapplication.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta Base (Tus colores del Wireframe y Logo)
val AppBeige = Color(0xFFF5F5DC) // Fondo Beige Medio
val AppTextBlack = Color(0xFF212121) // Negro Suave
val SelectorBlue = Color(0xFF1A237E) // Azul Oscuro (Selectores y Timer)
val SharePurple = Color(0xFF7E57C2) // Lila del logo
val ReturnPink = Color(0xFFEC407A) // Rosa del logo

// Paleta de Categorías (Para los botones de respuestas)
object CategoryColors {
    val All = Color(0xFF607D8B)
    val History = Color(0xFF795548)
    val Science = Color(0xFF4CAF50)
    val Sports = Color(0xFFFF9800)
    val Geography = Color(0xFF2196F3)
    val Entertainment = Color(0xFFE91E63)
    val Art = Color(0xFF9C27B0)
}

// Paleta de Dificultades
object DifficultyColors {
    val Easy = Color(0xFF4CAF50)    // Verde
    val Medium = Color(0xFFFF9800)  // Naranja
    val Hard = Color(0xFFF44336)    // Rojo
}