package com.example.myapplication.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties

// Le cambiamos el nombre para que no haya conflictos
fun Categories.getCategoryColor(): Color {
    return when (this) {
        Categories.ALL -> Color(0xFF607D8B)
        Categories.HISTORY -> Color(0xFF795548)
        Categories.SCIENCE -> Color(0xFF4CAF50)
        Categories.SPORTS -> Color(0xFFFF9800)
        Categories.GEOGRAPHY -> Color(0xFF2196F3)
        Categories.ENTERTAINMENT -> Color(0xFFE91E63)
        Categories.ART -> Color(0xFF9C27B0)
    }
}

fun Difficulties.getDifficultyColor(): Color {
    return when (this) {
        Difficulties.ALL -> Color(0xFF607D8B)
        Difficulties.EASY -> Color(0xFF4CAF50)   // Verde (Ajusta a tus colores reales)
        Difficulties.MEDIUM -> Color(0xFFFF9800) // Naranja
        Difficulties.DIFFICULT -> Color(0xFFF44336)   // Rojo
        // Añade cualquier otra dificultad que tengas en tu enum
    }
}