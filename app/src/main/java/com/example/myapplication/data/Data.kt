package com.example.myapplication.data

data class Question(
    val statement: String,
    val answers: List<String>,
    val categories: Categories,
    val difficulties: Difficulties
) {
    // The first answer is always the right one.
    val correctAnswer: String = answers[0]

    // Gives the options disordered when the UI aks for it.
    fun getShuffledAnswers(): List<String> {
        return answers.shuffled()
    }
}

