package com.demo.compose.state

import androidx.lifecycle.ViewModel

class LatestNewsViewModel(
    private val repository: NewsRepository
): ViewModel() {

    fun refreshNews() {

    }

    val newsListUiItems = repository.latestNews().map { news ->
        NewsItemUiState(
            id = news.id,
            title = news.title,
            body = news.body,
            bookmarked = news.bookmarked,
            // Business logic is passed as a lambda function that the
            // UI calls on click events.
            onBookmark = {
                repository.addBookmark(news.id)
            }
        )
    }
}