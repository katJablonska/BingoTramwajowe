package com.example.bingo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bingo.ui.theme.Screen
import kotlinx.coroutines.delay
import com.example.bingo.ui.theme.MpkYellow

@Composable
fun LoadingScreen() {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3600)
        isLoading = false
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray), // Ustawienie koloru tła na lightgray
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
        }
    } else {
        Navigation()
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route) {
            DetailScreen(navController = navController)
        }
        composable(route = Screen.WinningScreen.route) {
            WinningScreen(navController = navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(100.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Wsiądź do tramwaju!",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun DetailScreen(navController: NavController) {
    val boardSize = 5
    val bingoWords = remember { generateBingoWords(boardSize) }
    val clickedCells = remember { mutableStateOf(Array(boardSize) { BooleanArray(boardSize) }) }
    var bingo by remember { mutableStateOf(false) }

    fun checkBingo(): Boolean {
        for (i in 0 until boardSize) {
            if ((0 until boardSize).all { clickedCells.value[i][it] } || (0 until boardSize).all { clickedCells.value[it][i] }) {
                return true
            }
        }
        return false
    }

    fun onCellClick(row: Int, col: Int) {
        val newClickedCells = clickedCells.value.map { it.copyOf() }.toTypedArray()
        newClickedCells[row][col] = !newClickedCells[row][col]
        clickedCells.value = newClickedCells
        bingo = checkBingo()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.DetailScreen.route) {
                        popUpTo(Screen.DetailScreen.route) { inclusive = true }
                    }
                },
            ) {
                Text(
                    text = "Nowa gra",
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BingoBoard(bingoWords, clickedCells.value, ::onCellClick)

        if (bingo) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.WinningScreen.route) }) {
                Text(
                    text = "BINGO!",
                    fontSize = 24.sp
                )
            }
        }
    }
}
@Composable
fun WinningScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.file),
            contentDescription = "Winning Image",
            modifier = Modifier.size(400.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.route) {
                    popUpTo(Screen.DetailScreen.route) { inclusive = true }
                }
            }
        ) {
            Text(
                text = "Zagraj ponownie",
                fontSize = 20.sp
            )
        }
    }
}