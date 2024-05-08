package com.demo.compose.lifecycle

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

/**
 * from
 * https://blog.csdn.net/hong_world/article/details/136549241
 */
@Preview
@Composable
fun StateChangeComposable() {
    Log.i("COMPOSE", "stateChangeComposable run1")
    var num by remember {
        mutableStateOf(1)
    }
    Column {
        Log.i("COMPOSE", "stateChangeComposable run2")
        Button(onClick = {
            Log.i("COMPOSE", "stateChangeComposable run click")
            num++
        }) {
            Log.i("COMPOSE", "stateChangeComposable run3")
            Text(text = "change")
        }
        Log.i("COMPOSE", "stateChangeComposable run4")
        Text(text = "current num = $num")
    }
}


@Preview
@Composable
fun StateChangeComposable2() {
    Log.i("COMPOSE", "stateChangeComposable run1")
    var num by remember {
        mutableStateOf(1)
    }
    Card {
        Log.i("COMPOSE", "stateChangeComposable run2")
        Button(onClick = {
            Log.i("COMPOSE", "stateChangeComposable run click")
            num++
        }) {
            Log.i("COMPOSE", "stateChangeComposable run3")
            Text(text = "change")
        }
        Log.i("COMPOSE", "stateChangeComposable run4")
        Text(text = "current num = $num")
    }
}