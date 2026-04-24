package com.example.myapplication.navigation

import androidx.navigation3.runtime.NavKey
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes : NavKey {
    @Serializable data object Menu : Routes
    @Serializable data class Game(
        val category: Categories,
        val difficulty: Difficulties,
        val id: Long = System.currentTimeMillis()
    ) : Routes
    @Serializable data class Result(val score: Int) : Routes
}