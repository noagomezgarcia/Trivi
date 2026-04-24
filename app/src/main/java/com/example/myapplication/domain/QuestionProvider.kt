package com.example.myapplication.domain

import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.data.Question

class QuestionProvider {
    private val allQuestions: List<Question>

    constructor(questions: List<Question>) {
        this.allQuestions = questions
    }

    /**
     * Devuelve las preguntas filtradas.
     * @param quantity El número máximo de preguntas a devolver (por defecto es 10).
     */
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
            .shuffled()        //mezclamos todas las que pasaron el filtro
            .take(quantity)    //tomamos solo la cantidad que nos han pedido
    }
}