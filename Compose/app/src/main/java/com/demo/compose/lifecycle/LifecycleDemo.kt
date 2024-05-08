package com.demo.compose.lifecycle

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

suspend fun request() {
    delay(2000)
    Log.i("COMPOSE", "request finished")
}

@Preview
@Composable
fun LifecycleDemo() {
    var clickCount by remember { mutableStateOf(0) }

    Log.i("COMPOSE", "composition")

    LaunchedEffect(Unit) {
        Log.i("COMPOSE", "LaunchedEffect()")
        request()
    }

    DisposableEffect(Unit) {

        Log.i("COMPOSE", "DisposableEffect() like onActive() can do init work, call before LaunchedEffect()")


        onDispose {
            Log.i("COMPOSE", "onDispose() cleaning")
        }
    }

    Column {

        Log.i("COMPOSE", "Column composition")

        Text("LifecycleDemo clickCount=$clickCount")

        Button(onClick = { clickCount++ }) {
            Log.i("COMPOSE", "Button composition")
            Text("Click")
        }
    }
}
