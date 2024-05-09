package com.demo.compose.viewmodels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class SavedStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                SavedStateScreen()
            }
        }
    }
}

@Composable
fun SavedStateScreen(
    viewModel: SavedStateFlowViewModel = viewModel()
) {
    val list = viewModel.filteredData.collectAsState(emptyList()).value

    LazyColumn {
        item {
            Button(onClick = { viewModel.setQuery("1") }) {
                Text("Filter")
            }
        }
        items(list) {
            Text(it)
        }
    }
}

/**
 * StateFlow in SavedStateHandle
 */
@HiltViewModel
class SavedStateFlowViewModel @Inject constructor(
    private val repository: DemoRepository = DemoRepository(),
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredData: Flow<List<String>> =
        savedStateHandle.getStateFlow<String>("query", "")
            .flatMapLatest { query ->
                repository.getFilteredDataFlow(query)
            }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }
}


@Singleton
class DemoRepository @Inject constructor(){
    fun getFilteredData(query: String):LiveData<List<String>> {
        return liveData { listOf("1", "2", "3") }
    }

    suspend fun getFilteredDataFlow(query: String): Flow<List<String>> {
        return if(query.isEmpty()) {
            flowOf(listOf("1", "2", "3", "4", "5"))
        } else {
            flowOf(listOf("1", "3", "5"))
        }
    }
}

/**
 * LiveData in SavedStateHandle
 */
class SavedStateViewModel(
    private val repository: DemoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val filteredData: LiveData<List<String>> =
        savedStateHandle.getLiveData<String>("query")
            .switchMap { query ->
                repository.getFilteredData(query)
            }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun saveStateHandelExample() {
        savedStateHandle.contains("query")
        savedStateHandle.remove<String>("query")
        savedStateHandle.keys()
    }
}
