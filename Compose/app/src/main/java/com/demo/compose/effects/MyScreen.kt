package com.demo.compose.effects

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Preview
@Composable
fun MyScreen() {
    var text by remember { mutableStateOf("init state") }

    LaunchedEffect(Unit) {
        // Perform an asynchronous operation
        val result = withContext(Dispatchers.IO) {
            // This will be executed on a background thread
            performLongRunningTask()
        }

        // Update the UI with the result
        text = result
    }

    // Display the result in the UI
    Text(text)
}

suspend fun performLongRunningTask(): String {
    // This simulates a long-running task, such as a network request or database operation
    delay(3000)
    return "Result from long-running task"
}