package com.demo.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.demo.compose.ui.pulltofresh.PullToFreshBasic

class PullRefreshActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToFreshBasic()
        }
    }
}

@Preview
@Composable
fun PullRefreshScreen() {
    PullToFreshBasic()
}

