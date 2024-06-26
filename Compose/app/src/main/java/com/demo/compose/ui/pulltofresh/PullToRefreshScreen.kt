/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.compose.ui.pulltofresh

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PullToRefreshScreen(
    viewModel: PullToRefreshViewModel = viewModel(),
) {
    PullToRefreshScreen(
        uiStateFlow = viewModel.uiState,
        onPullToRefresh = viewModel::refreshData,
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PullToRefreshScreen(
    uiStateFlow: StateFlow<UiState>,
    onPullToRefresh: () -> Unit,
) {
    Scaffold { padding ->

        val pullToRefreshState = rememberPullToRefreshState()

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(Unit) {
                onPullToRefresh()
            }
        }

        val uiState = uiStateFlow.collectAsState(UiState(false, emptyList()))

        Log.w("COMPOSE", "uiState.value.isRefreshing = ${uiState.value.isRefreshing}")

        LaunchedEffect(uiState.value.isRefreshing) {
            Log.w("COMPOSE", "LaunchedEffect(uiState.value.isRefreshing)")
            // uiState.value.isRefreshing 发生了变化，并且是变成了不是在刷新
            if (!uiState.value.isRefreshing) {
                Log.w("COMPOSE", "endRefresh()")
                pullToRefreshState.endRefresh()
            }
        }

        LaunchedEffect(Unit) {
            // 第一次进来开始刷新数据
            Log.w("COMPOSE", "LaunchedEffect(Unit)")
            pullToRefreshState.startRefresh()
            onPullToRefresh()
        }

        Box(
            modifier = Modifier
                .padding(padding)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(uiState.value.dataList) {
                    Text(it, Modifier.height(60.dp))
                }
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState
            )
        }
    }
}

