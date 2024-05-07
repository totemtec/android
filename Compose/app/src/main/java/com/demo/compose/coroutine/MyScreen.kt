package com.demo.compose.coroutine

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Preview
@Composable
fun MyScreen() {
    var count by remember { mutableStateOf(0) }

    Column {
        Text("Count: $count")

        Button(onClick = {
            // Launch a coroutine to increment the count
            CoroutineScope(Dispatchers.Default).launch {
                count++
            }
        }) {
            Text("Increment")
        }
    }
}


@Preview
@Composable
fun MyScreen2() {
    var isLoading by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf("") }

    Column {
        if (isLoading) {
            // Show a progress indicator while loading data
            CircularProgressIndicator()
        } else {
            // Show the loaded data
            Text(data)
        }

        Button(onClick = {
            isLoading = true
            CoroutineScope(Dispatchers.Default).launch {
                // Simulate loading data
                delay(2000)

                // Update data on the main thread
                withContext(Dispatchers.Main) {
                    data = "Loaded data"
                    isLoading = false
                }
            }
        }) {
            Text("Load Data")
        }
    }
}

@Preview
@Composable
fun MyComposable() {
    val myState = remember { mutableStateOf("initial state") }

    LaunchedEffect(Unit) {
        // Call the suspend function using launch
        val result = withContext(Dispatchers.IO) {
            mySuspendFunction()
        }
        myState.value = result
    }
    
    Text(text = myState.value)
}

suspend fun mySuspendFunction(): String {
    // Perform some long-running operation here
    delay(2000)
    return "new state"
}