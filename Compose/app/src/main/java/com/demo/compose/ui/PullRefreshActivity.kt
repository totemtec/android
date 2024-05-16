package com.demo.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PullRefreshActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullRefreshScreen()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PullRefreshScreen() {
    val viewModel = viewModel<PullRefreshViewModel>()
    val state by viewModel.state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = viewModel::loadOrders
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            items(state.dataList) {
                ListItem(
                    headlineContent = { Text(text = it ) },
                )
            }
        }

        PullRefreshIndicator(
            refreshing = viewModel.state.value.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (viewModel.state.value.isLoading) Color.Red else Color.Green,
        )
    }
}


data class PullRefreshState(
    val isLoading: Boolean = false,
    val dataList: List<String> = emptyList()
)

class PullRefreshViewModel : ViewModel() {
    private val _state = mutableStateOf(PullRefreshState())
    val state: State<PullRefreshState> = _state

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(1_000L)
            _state.value = _state.value.copy(
                isLoading = false,
                dataList = _state.value.dataList.toMutableList().also {
                    it.add(
                        index = 0,
                        element = "Order #${it.size + 1}"
                    )
                }
            )
        }
    }
}