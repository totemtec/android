package com.demo.compose.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.currentStateAsState

class LifecycleStateActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifecycleStateScreen()
//            LifecycleEventScreen()
        }
    }
}

/**
 * from
 * https://stackoverflow.com/questions/66546962/jetpack-compose-how-do-i-refresh-a-screen-when-app-returns-to-foreground
 * https://developer.android.com/topic/libraries/architecture/compose
 */
@Preview
@Composable
fun LifecycleStateScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        // Do something with your state
        // You may want to use DisposableEffect or other alternatives
        // instead of LaunchedEffect
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> { Log.d("COMPOSE", "LifecycleStateScreen: ON DESTROYED") }
            Lifecycle.State.INITIALIZED -> { Log.d("COMPOSE", "LifecycleStateScreen: ON INITIALIZED") }
            Lifecycle.State.CREATED -> { Log.d("COMPOSE", "LifecycleStateScreen: ON CREATED") }
            Lifecycle.State.STARTED -> { Log.d("COMPOSE", "LifecycleStateScreen: ON STARTED") }
            Lifecycle.State.RESUMED -> { Log.d("COMPOSE", "LifecycleStateScreen: ON RESUMED") }
        }
    }

    Text("Please see logcat debug log, tag is COMPOSE")
}

@Preview
@Composable
fun LifecycleEventScreen(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val lifecycleState = lifecycleOwner.lifecycle.currentStateAsState()

//    Log.d("COMPOSE", "LifecycleStateScreen: ${lifecycleState.value}")

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_CREATE")
    }
//
//    LifecycleEventEffect(Lifecycle.Event.ON_START) {
//        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_START")
//    }
//
//    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
//        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_RESUME")
//    }
//
//    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
//        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_PAUSE")
//    }
//
//    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
//        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_STOP")
//    }
//
//    LifecycleEventEffect(Lifecycle.Event.ON_DESTROY) {
//        Log.d("COMPOSE", "LifecycleEventScreen: Event.ON_DESTROY")
//    }

//    Column {
//        Text("Please see logcat debug log, tag is COMPOSE")
//    }
}


@Preview
@Composable
fun LifecycleEventScreen2() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = lifecycleOwner.lifecycle.currentStateAsState()

    LifecycleStartEffect("") {
        // ON_START code is executed here

        onStopOrDispose {
            // do any needed clean up here
        }
    }

    LifecycleResumeEffect("") {
        // ON_RESUME code is executed here

        onPauseOrDispose {
            // do any needed clean up here
        }
    }

    Text("Please see logcat debug log, tag is COMPOSE")
}