package com.example.bingo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bingo.ui.theme.Screen
import kotlinx.coroutines.delay
import com.example.bingo.ui.theme.MpkGreen
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
        composable(
            route = Screen.DetailScreen.route,
        ) {
            DetailScreen(navController = navController)
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

        BingoBoard(bingoWords)
    }
}

@Composable
fun BingoBoard(words: Array<Array<String>>) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.LightGray)
    ) {
        for (row in words) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (word in row) {
                    BingoCell(word = word)
                }
            }
        }
    }
}

@Composable
fun BingoCell(word: String) {
    var isClicked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .padding(1.dp)
            .border(1.dp, Color.Black)
            .background(if (isClicked) MpkYellow else Color.White)
            .clickable { isClicked = !isClicked }
    ) {
        Text(
            text = word,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(3.dp)
        )
    }
}

fun generateBingoWords(boardSize: Int): Array<Array<String>> {
    val numberToWordMap = mapOf(
        1 to "Tatra",
        2 to "Niebieska Gamma",
        3 to "Czerwony, krótki tramwaj",
        4 to "Zabytkowa bimba",
        5 to "Nocny",
        6 to "Gąsienica",
        7 to "105n",
        8 to "Starsza pani, której się należy",
        9 to "Zmęczone torby na siedzeniach",
        10 to "Tłok",
        11 to "Głośni nastolatkowie",
        12 to "Menel z piwkiem",
        13 to "Kanar",
        14 to "Wycieczka przedszkolaków",
        15 to "Kibice w drodze na mecz",
        16 to "Głośna rozmowa przez telefon",
        17 to "KONTROLA KASOWNIKÓW",
        18 to "Masz plecak? Zdejmij go!",
        19 to "Przechodząc przez torowisko...",
        20 to "Ile masz polubień w necie?",
        21 to "Rozliczasz podatki w Poznaniu?",
        22 to "Inaczej kursować będą tramwaje linii...",
        23 to "Jeśli to możliwe, przesuńmy się do wnętrza...",
        24 to "Spóźnienie",
        25 to "Wykolejenie",
        26 to "Stłuczka",
        27 to "Awaria",
        28 to "Ogrzewanie włączone latem",
        29 to "Milion stopni",
        30 to "Rozlane piwo",
        31 to "Dwie 6 pod rząd",
        32 to "Filmiki z wypadkami",
        33 to "Ktoś zasnął",
        34 to "Ostre hamowanie"
    )

    val numbers = (1..34).shuffled().take(boardSize * boardSize)
    val board = Array(boardSize) { Array(boardSize) { "" } }
    var index = 0

    for (i in 0 until boardSize) {
        for (j in 0 until boardSize) {
            board[i][j] = numberToWordMap[numbers[index++]] ?: ""
        }
    }

    return board
}
