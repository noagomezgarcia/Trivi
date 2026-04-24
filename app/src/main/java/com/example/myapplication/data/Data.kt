package com.example.myapplication.data

data class Question(
    val statement: String,
    val answers: MutableList<String>,
    val categories: Categories,
    val difficulties: Difficulties
) {
    //La primera respuesta siempre es la correcta
    val correctAnswer: String = answers[0]

    //Da las opciones desordenadas justo cuando la UI lo pide
    fun getShuffledAnswers(): List<String> {
        return answers.shuffled()
    }
}

