package com.demo.compose.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MyRepository {
    fun searchNews():List<String> {
        return listOf("news 1", "news 2")
    }
}

// singleton instance in app scope
val singletonRepository = MyRepository()


class MyViewModel(
    private val myRepository: MyRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ViewModel logic
    val newsList = myRepository.searchNews()

    companion object {

        val JavaFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return MyViewModel(
//                    (application as MyApplication).myRepository,
                    singletonRepository,
                    savedStateHandle
                ) as T
            }
        }

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
//                val myRepository = (this[APPLICATION_KEY] as MyApplication).myRepository
                val myRepository = singletonRepository
                MyViewModel(
                    myRepository = myRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = viewModel(factory = MyViewModel.Factory)
//  viewModel: MyViewModel = viewModel(factory = MyViewModel.JavaFactory)
) {
    // ...
}