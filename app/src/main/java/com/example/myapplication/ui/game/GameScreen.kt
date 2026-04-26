package com.example.myapplication.ui.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import getCategoryColor


@Composable
fun GameScreen(
    gameId: Long,
    category: Categories,
    difficulty: Difficulties,
    onGameFinished: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val gameViewModel: GameScreenViewModel = viewModel(
        key = gameId.toString(),
        factory = GameScreenViewModelFactory(category, difficulty)
    )

    val currentRound by gameViewModel.currentRound.collectAsStateWithLifecycle()
    val totalRounds by gameViewModel.totalRounds.collectAsStateWithLifecycle()
    val questionStatement by gameViewModel.questionStatement.collectAsStateWithLifecycle()
    val answers by gameViewModel.answers.collectAsStateWithLifecycle()
    val timeLeft by gameViewModel.timeLeft.collectAsStateWithLifecycle()
    val score by gameViewModel.score.collectAsStateWithLifecycle()
    val currentQuestionCategory by gameViewModel.currentQuestionCategory.collectAsStateWithLifecycle()
    val currentCategoryColor = currentQuestionCategory.getCategoryColor()
    val isGameOver by gameViewModel.isGameOver.collectAsStateWithLifecycle()
    val selectedAnswer by gameViewModel.selectedAnswer.collectAsStateWithLifecycle()
    val correctAnswer by gameViewModel.correctAnswer.collectAsStateWithLifecycle()

    LaunchedEffect(isGameOver) {
        if (isGameOver) {
            onGameFinished(gameViewModel.score.value)
        }
    }

    if (!isGameOver) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "Round $currentRound/$totalRounds",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = questionStatement,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(36.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                answers.chunked(2).forEach { rowAnswers ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowAnswers.forEach { answer ->
                            /* Determine button color based on feedback state:
                            - No answer selected yet: use the category color
                            - This button is the correct answer: green
                            - This button was the wrong answer selected: red
                            - Any other button while feedback is showing: dimmed */
                            val buttonColor = when {
                                selectedAnswer == null -> currentCategoryColor
                                answer == correctAnswer -> Color(0xFF4CAF50)
                                answer == selectedAnswer -> Color(0xFFF44336)
                                else -> currentCategoryColor
                            }

                            AnswerButton(
                                text = answer,
                                containerColor = buttonColor,
                                // Block further clicks while feedback is visible
                                onClick = {
                                    if (selectedAnswer == null) gameViewModel.checkAnswer(answer)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowAnswers.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            TimerProgressBar(
                timeLeft = timeLeft,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.weight(1.3f))
        }
    }
}

@Composable
fun AnswerButton(
    text: String,
    containerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun TimerProgressBar(
    timeLeft: Float,
    color: Color,
    totalTimeSeconds: Float = 10f
) {
    val progress = timeLeft / totalTimeSeconds
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        label = "timer_progress"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(12.dp)
            .clip(RoundedCornerShape(50)),
        color = color,
        trackColor = Color(0xFFE0E0E0)
    )
}