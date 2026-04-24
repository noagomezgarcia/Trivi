package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.entryProvider
import com.example.myapplication.ui.game.GameScreen
import com.example.myapplication.ui.menu.MenuScreen
import com.example.myapplication.ui.result.ResultScreen
import java.util.Map.entry

@Composable
fun NavigationWrapper() {
    val backStack = rememberNavBackStack(Routes.Menu)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Routes.Menu> {
                MenuScreen(
                    navigateToGame = { category, difficulty ->
                        backStack.add(Routes.Game(category, difficulty))
                    }
                )
            }

            entry<Routes.Game> { key ->
                androidx.compose.runtime.key(key.id) {
                    GameScreen(
                        gameId = key.id,
                        category = key.category,
                        difficulty = key.difficulty,
                        onGameFinished = { finalScore ->
                            backStack.removeLastOrNull()
                            backStack.add(Routes.Result(finalScore))
                        }
                    )
                }
            }

            entry<Routes.Result> { key ->
                ResultScreen(
                    score = key.score,
                    navigateToMenu = {
                        backStack.clear()
                        backStack.add(Routes.Menu)
                    }
                )
            }
        }
    )
}