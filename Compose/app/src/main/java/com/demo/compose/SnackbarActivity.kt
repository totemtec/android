package com.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.demo.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.launch

class SnackbarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
//                SnackbarDemo()
                SnackbarWithActionDemo()
            }
        }
    }
}

@Composable
fun SnackbarDemo() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("This is a Snackbar")
                    }
                }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "")
            }
        }
    ) { contentPadding ->
        Text(text = "Content", Modifier.padding(contentPadding))
    }
}

@Composable
fun SnackbarWithActionDemo() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = "This is a Snackbar",
                                actionLabel = "Delete",
                                withDismissAction = true
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                /* Handle snackbar action performed */
                            }

                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                            }
                        }
                    }
                }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "")
            }
        }
    ) { contentPadding ->
        Text(text = "Content", Modifier.padding(contentPadding))
    }
}
