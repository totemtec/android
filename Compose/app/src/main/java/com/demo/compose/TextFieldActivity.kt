package com.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class TextFieldActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFieldExample()
        }
    }
}

@Preview
@Composable
fun TextFieldExample() {
    var text by remember { mutableStateOf("hello") }
    var basicText by remember { mutableStateOf("Basic Text Field") }

    Column {
        TextField(
            value = text,
            onValueChange = { text = it }
        )

        Spacer(Modifier.size(20.dp))

        BasicTextField(value = basicText, onValueChange = {
            basicText = it
        })
    }
}