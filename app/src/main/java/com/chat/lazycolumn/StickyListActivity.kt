package com.chat.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chat.lazycolumn.ui.theme.LazyColumnTheme

val fruits = listOf(
    "A" to listOf(
        "Apple",
        "Avocado",
        "Apricot",
        "Asian Pear",
        "Annatto"
    ),
    "B" to listOf(
        "Banana",
        "Blueberry",
        "Blackberry",
        "Boysenberry",
        "Blood Orange"
    ),
    "C" to listOf(
        "Cherry",
        "Citrus",
        "Cantaloupe",
        "Cranberry",
        "Coconut"
    ),
    "D" to listOf(
        "Date",
        "Dragon Fruit",
        "Durian",
        "Damson",
        "Dewberry"
    ),
    "E" to listOf(
        "Elderberry",
        "Eggplant",
        "Fig",
        "Feijoa",
        "Finger Lime"
    ),
    "G" to listOf(
        "Grape",
        "Grapefruit",
        "Guava",
        "Gooseberry",
        "Granadilla"
    ),
    "K" to listOf(
        "Kiwi",
        "Kumquat",
        "Kiwano",
        "Karonda",
        "Kakadu Plum"
    ),
    "L" to listOf(
        "Lemon",
        "Lime",
        "Loquat",
        "Lychee",
        "Longan"
    ),
    "M" to listOf(
        "Mango",
        "Mandarin",
        "Melon",
        "Mulberry",
        "Mangosteen"
    ),
    "N" to listOf(
        "Nectarine",
        "Nance",
        "Nutmeg",
        "Nispero",
        "Noni"
    ),
    "O" to listOf(
        "Orange",
        "Olive",
        "Ohelo Berry",
        "Osage Orange",
        "Olive"
    ),
    "P" to listOf(
        "Papaya",
        "Peach",
        "Pear",
        "Plum",
        "Passion Fruit"
    ),
    "R" to listOf(
        "Raspberry",
        "Rambutan",
        "Redcurrant",
        "Rose Apple",
        "Rhubarb"
    ),
    "S" to listOf(
        "Strawberry",
        "Star Fruit",
        "Sapodilla",
        "Soursop",
        "Sugar Apple"
    ),
    "T" to listOf(
        "Tomato",
        "Tamarind",
        "Tangerine",
        "Tomato",
        "Tamarillo"
    ),
    "U" to listOf(
        "Ugli Fruit",
        "Ume",
        "Umbu",
        "Ugni Molinae",
        "Ugni Molinae"
    )
)

class StickyListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                StickyListScreen()
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyListScreen() {
    val lazyListState = rememberLazyListState()

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = lazyListState
        ) {
            fruits.forEach { (header, items) ->

                stickyHeader {
                    println("MaterialTheme.colorScheme.primary ${MaterialTheme.colorScheme.primary}")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = header,
                            color = Color.White
                        )
                    }
                }

                items(items) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = item)
                    }
                }
            }
        }
    }
}
