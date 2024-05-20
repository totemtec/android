package com.demo.compose.ui.pager

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Preview
@Composable
fun HorizontalPagerPreview() {
    SimplePager()
    PagerScrollToItem()
    PagerWithScrollNotify()
    PagerWithIndicator()
}

val colors = listOf(Color.Red, Color.Blue, Color.Cyan, Color.Gray, Color.Yellow,
    Color.DarkGray, Color.Green, Color.LightGray, Color.White, Color.Black)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimplePager() {
    val pagerStat = rememberPagerState{
        10
    }

    HorizontalPager(state = pagerStat) {page ->
        Text(text = "Page $page",
            modifier = Modifier
                .fillMaxSize()
                .background(colors[page]))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScrollToItem() {
    Box(modifier = Modifier.size(200.dp)) {
        val pagerState = rememberPagerState {
            10
        }

        VerticalPager(state = pagerState) {
            Text(text = "Page $it",
                Modifier
                    .fillMaxSize()
                    .background(colors[it]))
        }

        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(5)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "Jump to Page 5")
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerWithScrollNotify() {

    Box(modifier = Modifier.size(200.dp)) {
        val pagerState = rememberPagerState {
            10
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                Log.d("COMPOSE", "Page changed to $it")
            }
        }

        VerticalPager(state = pagerState) {
            Text(text = "Page $it",
                Modifier
                    .fillMaxSize()
                    .background(colors[it]))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerWithIndicator() {

    Box(modifier = Modifier.size(200.dp)) {
        val pagerState = rememberPagerState {
            4
        }

        HorizontalPager(state = pagerState,
            Modifier.fillMaxSize()) {
            Text(text = "Page $it",
                Modifier
                    .fillMaxSize()
                    .background(colors[it]))
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) {
                val color = if (pagerState.currentPage == it) Color.DarkGray else Color.LightGray
                Box(
                    Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}

