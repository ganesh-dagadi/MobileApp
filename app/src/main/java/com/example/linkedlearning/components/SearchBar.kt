package com.example.linkedlearning.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SearchBar(
    enteredText : (text:String)-> Unit
){
    val textState = remember { mutableStateOf(TextFieldValue("")) }


    Row(modifier = Modifier
        .width(400.dp)
        .padding(20.dp)
        .height(50.dp)
        .border(1.dp, MaterialTheme.colors.primaryVariant, shape = RoundedCornerShape(10.dp))) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(235 , 235 , 235)
            ),
            placeholder = { Text("Search") }
        )

        IconButton(onClick = {
            textState.value = TextFieldValue("")
        }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Clear search text",
                tint = if (textState.value.text.isEmpty()) Color.Transparent else Color.Black
            )
        }

        IconButton(onClick = {
            enteredText(textState.value.text)
        }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Perform search"
            )
        }
    }
}