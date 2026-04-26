package com.example.myapplication.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.data.Question
import com.example.myapplication.data.QuestionsRepository
import com.example.myapplication.domain.QuestionProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val category: Categories,
    private val difficulty: Difficulties
) : ViewModel() {

    private val provider = QuestionProvider(QuestionsRepository.questionList)

    private var questionsForThisGame: List<Question> = emptyList()
    private var questionIndex = 0
    private var currentQuestion: Question? = null

    // Timer job reference so we can cancel it when needed
    private var timerJob: Job? = null
    private val totalTime = 10f

    // UI states
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

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    // Tracks the category of the current question to color the answer buttons
    private val _currentQuestionCategory = MutableStateFlow(category)
    val currentQuestionCategory: StateFlow<Categories> = _currentQuestionCategory.asStateFlow()

    // Holds the answer the user just selected, null when no answer is pending feedback
    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    // Exposes the correct answer so the UI can highlight it during feedback
    private val _correctAnswer = MutableStateFlow<String?>(null)
    val correctAnswer: StateFlow<String?> = _correctAnswer.asStateFlow()

    // Signals that the game has ended and the result screen should be shown
    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    init {
        startGame()
    }

    fun startGame(quantityOfQuestions: Int = 10) {
        questionIndex = 0
        _score.value = 0
        _currentRound.value = 1
        _isGameOver.value = false
        _timeLeft.value = totalTime
        _selectedAnswer.value = null
        _correctAnswer.value = null
        timerJob?.cancel()

        questionsForThisGame = provider.getFilteredQuestions(
            categories = category,
            difficulties = difficulty,
            quantity = quantityOfQuestions
        )

        _totalRounds.value = questionsForThisGame.size

        if (questionsForThisGame.isNotEmpty()) {
            loadCurrentQuestion()
        } else {
            _isGameOver.value = true
        }
    }

    private fun loadCurrentQuestion() {
        currentQuestion = questionsForThisGame[questionIndex]
        currentQuestion?.let { question ->
            _questionStatement.value = question.statement
            _answers.value = question.getShuffledAnswers()
            _currentQuestionCategory.value = question.categories
            _correctAnswer.value = question.correctAnswer
            _selectedAnswer.value = null
            startTimer()
        }
    }

    // Uses a start timestamp instead of subtracting fixed increments,
    // which avoids drift caused by coroutine scheduling delays
    private fun startTimer() {
        timerJob?.cancel()
        _timeLeft.value = totalTime
        val startTime = System.currentTimeMillis()

        timerJob = viewModelScope.launch {
            while (true) {
                delay(50L)
                val elapsed = (System.currentTimeMillis() - startTime) / 1000f
                val remaining = totalTime - elapsed
                if (remaining <= 0f) {
                    _timeLeft.value = 0f
                    checkAnswer("")
                    break
                }
                _timeLeft.value = remaining
            }
        }
    }

    fun checkAnswer(selected: String) {
        timerJob?.cancel()
        _selectedAnswer.value = selected

        if (selected == currentQuestion?.correctAnswer) {
            _score.value += 1
        }

        // Show feedback for 800ms before moving to the next question
        viewModelScope.launch {
            delay(800L)
            _selectedAnswer.value = null
            getNextQuestion()
        }
    }

    private fun getNextQuestion() {
        if (questionIndex < questionsForThisGame.size - 1) {
            questionIndex++
            _currentRound.value++
            loadCurrentQuestion()
        } else {
            _isGameOver.value = true
        }
    }
}