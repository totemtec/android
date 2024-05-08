package com.demo.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.demo.compose.ui.theme.ComposeTheme

class ButtonActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonExample()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun ButtonExample() {
    ComposeTheme {
        FlowRow() {
            Button(onClick = {
                Log.i("BUTTON", "Button clicked")
            }) {
                Text("Default")
            }

            FilledTonalButton(onClick = {
                Log.i("BUTTON", "Filled Tonal Button clicked")
            }) {
                Text("Tonal")
            }

            OutlinedButton(onClick = {
                Log.i("BUTTON", "Outlined Button clicked")
            }) {
                Text("Tonal")
            }

            ElevatedButton(onClick = {
                Log.i("BUTTON", "Elevated Button clicked")
            }) {
                Text("Elevated")
            }

            TextButton(onClick = {
                Log.i("BUTTON", "Text Button clicked")
            }) {
                Text("Text")
            }
        }
    }
}