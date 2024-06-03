package com.example.bingo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bingo.ui.theme.MpkYellow

@Composable
fun BingoBoard(words: Array<Array<String>>, clickedCells: Array<BooleanArray>, onCellClick: (Int, Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.LightGray)
    ) {
        for (rowIndex in words.indices) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (colIndex in words[rowIndex].indices) {
                    BingoCell(
                        word = words[rowIndex][colIndex],
                        isClicked = clickedCells[rowIndex][colIndex],
                        onClick = { onCellClick(rowIndex, colIndex) }
                    )
                }
            }
        }
    }
}

@Composable
fun BingoCell(word: String, isClicked: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(110.dp)
            .padding(1.dp)
            .border(1.dp, Color.Black)
            .background(if (isClicked) MpkYellow else Color.White)
            .clickable { onClick() }
    ) {
        Text(
            text = word,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(3.dp)
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
        7 to "105Na",
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
