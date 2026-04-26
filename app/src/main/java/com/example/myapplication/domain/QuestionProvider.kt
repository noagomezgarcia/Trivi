package com.example.myapplication.domain

import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.data.Question

class QuestionProvider {
    private val allQuestions: List<Question>

    constructor(questions: List<Question>) {
        this.allQuestions = questions
    }

    fun getFilteredQuestions(
        categories: Categories,
        difficulties: Difficulties,
        quantity: Int = 10
    ): List<Question> {
        return allQuestions.filter { question ->
            val matchesCategory = (categories == Categories.ALL) || (question.categories == categories)
            val matchesDifficulty = (difficulties == Difficulties.ALL) || (question.difficulties == difficulties)

            matchesCategory && matchesDifficulty
        }
            .shuffled()
            .take(quantity) }
}