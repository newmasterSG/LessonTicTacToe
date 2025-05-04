package com.example.lessontictactoe

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lessontictactoe.ui.theme.LessonTicTacToeTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreen(modifier: Modifier= Modifier)
{
    Column(modifier=modifier) {
        Text(
            text="Tic Tac Toe",
            style = MaterialTheme.typography.headlineMedium,
            modifier= Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign= TextAlign.Center
        )
    }
    var boardSize by remember { mutableStateOf<Int?>(null) }

    if (boardSize == null) {
        StartScreen(onStartGame = { selectedSize -> boardSize = selectedSize })
    } else {
        GameBoard(dim = boardSize!!)
    }
}

@Composable
fun GameBoard(dim: Int) {
    val field = remember { mutableStateListOf(*Array(dim * dim) { "_" }) }
    var currentPlayer by remember { mutableStateOf("X") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Хід гравця: $currentPlayer", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(12.dp))

        for (row in 0 until dim) {
            Row {
                for (col in 0 until dim) {
                    val index = row * dim + col
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(4.dp)
                            .border(2.dp, MaterialTheme.colorScheme.primary)
                            .clickable {
                                if (field[index] == "_") {
                                    field[index] = currentPlayer
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = field[index],
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun StartScreen(onStartGame: (Int) -> Unit) {
    var selectedSize by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Виберіть розмір поля", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Row {
            listOf(3, 4, 5).forEach { size ->
                Button(
                    onClick = { selectedSize = size },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSize == size) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("${size}x$size")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = { onStartGame(selectedSize) }) {
            Text("Почати гру")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview()
{
    LessonTicTacToeTheme {
        MainScreen()
    }
}