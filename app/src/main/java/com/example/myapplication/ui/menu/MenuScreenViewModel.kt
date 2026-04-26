package com.example.myapplication.ui.menu

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuScreenViewModel : ViewModel() {

    private val _chosenCategory = MutableStateFlow(Categories.ALL)
    val chosenCategory = _chosenCategory.asStateFlow()

    private val _chosenDifficulty = MutableStateFlow(Difficulties.ALL)
    val chosenDifficulty = _chosenDifficulty.asStateFlow()

    fun updateCategory(newCategory: Categories) {
        _chosenCategory.value = newCategory
    }

    fun updateDifficulty(newDifficulty: Difficulties) {
        _chosenDifficulty.value = newDifficulty
    }
}