package com.demo.compose.ui.pulltofresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState (val isRefreshing: Boolean, val dataList: List<String>)

class GalleryViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState(false, emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isRefreshing = true
            )

            try {
                delay(3000)
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    dataList = listOf((10..20).random().toString(), "2", "3", "4", "5", "2", "3", "4", "5", "2", "3", "4", "5")
                )

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false
                )
            }
        }
    }
}