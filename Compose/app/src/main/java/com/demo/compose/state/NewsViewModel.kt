package com.demo.compose.state


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

data class Message(val message: String)

data class NewsUiState(
    val isFetchingArticles: Boolean = false,
    val isSignedIn: Boolean = false,
    val isPremium: Boolean = false,
    val newsItems: List<NewsItemUiState> = listOf(),
    val userMessages: List<Message> = listOf()
)

val NewsUiState.canBookmarkNews: Boolean get() = isSignedIn && isPremium

data class NewsItemUiState(
    val id: Int,
    val title: String,
    val body: String,
    val bookmarked: Boolean = false,
    val onBookmark: () -> Unit = {}
)

public class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    var uiState by mutableStateOf(NewsUiState(isFetchingArticles = true))
        private set

    init {
        fetchArticles("")
    }

    private var fetchJob: Job? = null

    fun fetchArticles(category: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val newsItems = repository.newsItemsForCategory(category)
                uiState = uiState.copy(newsItems = newsItems, isFetchingArticles = false)
            } catch (ioe: IOException) {
                // Handle the error and notify the UI when appropriate.
                val messages = listOf(Message(ioe.message ?: ioe.javaClass.simpleName))
                uiState = uiState.copy(userMessages = messages, isFetchingArticles = false)
            }
        }
    }
}