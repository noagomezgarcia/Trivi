package com.example.myapplication.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.myapplication.R
import com.example.myapplication.data.Categories
import com.example.myapplication.data.Difficulties
import com.example.myapplication.ui.utils.*

@Composable
fun MenuScreen(
    navigateToGame: (Categories, Difficulties) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MenuScreenViewModel = viewModel()
) {
    val selectedCategory by viewModel.chosenCategory.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.chosenDifficulty.collectAsStateWithLifecycle()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.logo))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "TRIVIAL",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(220.dp).padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CategoryDropdownMenu(
            items = Categories.entries,
            selectedItem = selectedCategory,
            onItemSelection = { viewModel.updateCategory(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        DifficultyDropdownMenu(
            items = Difficulties.entries,
            selectedItem = selectedDifficulty,
            onItemSelection = { viewModel.updateDifficulty(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = { navigateToGame(selectedCategory, selectedDifficulty) },
            modifier = Modifier.fillMaxWidth(0.9f).height(90.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "PLAY", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownMenu(
    items: List<Categories>,
    selectedItem: Categories,
    onItemSelection: (Categories) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val currentColor = selectedItem.getCategoryColor()

    Column(modifier = modifier) {
        Text(text = "Category", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = currentColor,
                    unfocusedBorderColor = currentColor,
                    focusedTextColor = currentColor,
                    unfocusedTextColor = currentColor
                ),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item.name,
                                color = item.getCategoryColor(),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = {
                            onItemSelection(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyDropdownMenu(
    items: List<Difficulties>,
    selectedItem: Difficulties,
    onItemSelection: (Difficulties) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val currentColor = selectedItem.getDifficultyColor()

    Column(modifier = modifier) {
        Text(text = "Dificulty", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = currentColor,
                    unfocusedBorderColor = currentColor,
                    focusedTextColor = currentColor,
                    unfocusedTextColor = currentColor
                ),
                textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item.name,
                                color = item.getDifficultyColor(),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = {
                            onItemSelection(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}