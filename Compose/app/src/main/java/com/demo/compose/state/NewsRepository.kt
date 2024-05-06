package com.demo.compose.state

class NewsRepository {
    fun newsItemsForCategory(category: String): List<NewsItemUiState> {
        return listOf(
            NewsItemUiState(1, "title 1", "body 1"),
            NewsItemUiState(2, "title 2", "body 2"),
            NewsItemUiState(3, "title 3", "body 3"),
            NewsItemUiState(4, "title 4", "body 4"),
        )
    }

    fun latestNews(): List<NewsItemUiState> {
        return listOf(
            NewsItemUiState(1, "title 1", "body 1"),
            NewsItemUiState(2, "title 2", "body 2"),
            NewsItemUiState(3, "title 3", "body 3"),
            NewsItemUiState(4, "title 4", "body 4"),
        )
    }

    fun addBookmark(id: Int) {

    }
}