import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.ui.theme.CategoryColors
import com.example.myapplication.ui.theme.DifficultyColors

fun Categories.getCategoryColor(): androidx.compose.ui.graphics.Color {
    return when (this) {
        Categories.ALL          -> CategoryColors.All
        Categories.HISTORY      -> CategoryColors.History
        Categories.SCIENCE      -> CategoryColors.Science
        Categories.SPORTS       -> CategoryColors.Sports
        Categories.GEOGRAPHY    -> CategoryColors.Geography
        Categories.ENTERTAINMENT -> CategoryColors.Entertainment
        Categories.ART          -> CategoryColors.Art
    }
}

fun Difficulties.getDifficultyColor():  androidx.compose.ui.graphics.Color {
    return when (this) {
        Difficulties.ALL       -> CategoryColors.All
        Difficulties.EASY      -> DifficultyColors.Easy
        Difficulties.MEDIUM    -> DifficultyColors.Medium
        Difficulties.DIFFICULT -> DifficultyColors.Hard
    }
}