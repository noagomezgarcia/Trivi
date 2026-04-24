package com.example.myapplication.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.data.Question
import com.example.myapplication.data.QuestionsRespository
import com.example.myapplication.domain.QuestionProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameScreenViewModel(private val category: Categories,
                          private val difficulty: Difficulties) : ViewModel() {
    private val provider = QuestionProvider(QuestionsRespository.questionList)

    private var questionsForThisGame: List<Question> = emptyList()
    private var questionIndex = 0
    private var currentQuestion: Question? = null

    //guardamos el trabajo del temporizador para poder cancelarlo
    private var timerJob: Job? = null
    private val totalTime = 10f // 10s por pregunta

    //estados
    private val _questionStatement = MutableStateFlow("")
    val questionStatement: StateFlow<String> = _questionStatement.asStateFlow()

    private val _answers = MutableStateFlow<List<String>>(emptyList())
    val answers: StateFlow<List<String>> = _answers.asStateFlow()

    private val _currentRound = MutableStateFlow(1)
    val currentRound: StateFlow<Int> = _currentRound.asStateFlow()

    private val _totalRounds = MutableStateFlow(10)
    val totalRounds: StateFlow<Int> = _totalRounds.asStateFlow()

    private val _timeLeft = MutableStateFlow(totalTime)
    val timeLeft: StateFlow<Float> = _timeLeft.asStateFlow()

    //para la pantalla de result
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // NUEVO: Estado para saber la categoría de la pregunta actual
    private val _currentQuestionCategory = MutableStateFlow(category)
    val currentQuestionCategory: StateFlow<Categories> = _currentQuestionCategory.asStateFlow()

    //saber cuando se ha acabado la partida y pasar a la result screen
    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    init {

        _isGameOver.value = false
        _score.value = 0
        startGame()

    }

    //inicia el juego y recibe la dificultad
    fun startGame(quantityOfQuestions: Int = 10) {
        // 1. Limpiamos estados de control
        questionIndex = 0
        _score.value = 0
        _currentRound.value = 1
        _isGameOver.value = false
        _timeLeft.value = totalTime
        timerJob?.cancel() //paramos cualquier timer viejo

        // 2. Obtenemos preguntas nuevas
        questionsForThisGame = provider.getFilteredQuestions(
            categories = category,
            difficulties = difficulty,
            quantity = quantityOfQuestions
        )

        _totalRounds.value = questionsForThisGame.size

        //crgamos la primera si hay preguntas
        if (questionsForThisGame.isNotEmpty()) {
            loadCurrentQuestion()
        } else {
            _isGameOver.value = true
        }
    }

    private fun startTimer() {
        //si había un temporizador anterior funcionando lo cancelamos
        timerJob?.cancel()
        _timeLeft.value = totalTime

        //
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(100L) //pausa de 100 milisegundos
                _timeLeft.value -= 0.1f //restamos tiempo
            }

            //si salimos del bucle el tiempo llegó a 0
            //lo tratamos como una respuesta incorrecta (enviamos texto vacío)
            checkAnswer("")
        }
    }

    //carga la pregunta actual y sus respuestas mezcladas
    private fun loadCurrentQuestion() {
        currentQuestion = questionsForThisGame[questionIndex]

        currentQuestion?.let { question ->
            _questionStatement.value = question.statement
            _answers.value = question.getShuffledAnswers()

            // NUEVO: Actualizamos el estado con la categoría de la pregunta actual
            _currentQuestionCategory.value = question.categories

            startTimer()
        }
    }

    //comprovamos si el usuario ha clicado en la respuesta correcta
    fun checkAnswer(selectedAnswer: String) {
        timerJob?.cancel() //parar el tiempo de inmediato

        val correctAnswer = currentQuestion?.correctAnswer
        if (selectedAnswer == correctAnswer) {
            _score.value += 1
        }
        getNextQuestion()
    }

    //avanza a la siguiente pregunta o finaliza el juego
    private fun getNextQuestion() {
        if (questionIndex < questionsForThisGame.size - 1) {
            questionIndex++
            _currentRound.value++
            loadCurrentQuestion()
        } else {
            //activamos el flag
            _isGameOver.value = true
        }
    }
}