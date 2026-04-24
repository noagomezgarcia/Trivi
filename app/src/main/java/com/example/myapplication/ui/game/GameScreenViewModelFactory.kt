package com.example.myapplication.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties

class GameScreenViewModelFactory(
    private val category: Categories,
    private val difficulty: Difficulties
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScreenViewModel::class.java)) {
            return GameScreenViewModel(category, difficulty) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}