package com.demo.compose.state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.compose.ui.theme.ComposeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SimpleStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTheme {
//                HelloContent1()
//                HelloContent2()
//                HelloContent3()
//                HelloScreen4()
                HelloScreen5()
            }
        }
    }
}

@Composable
fun HelloContent1() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Name") })
    }
}

@Composable
fun HelloContent2() {
    // not work on rotate device portrait and landscape
    var name: String by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello ${name}!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") })
    }
}

@Composable
fun HelloContent3() {
    // work on rotate device portrait and landscape
    var name: String by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello ${name}!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") })
    }
}

@Composable
fun HelloScreen4() {
    var name: String by rememberSaveable { mutableStateOf("") }
    HelloContent4(name, onNameChanged = { name = it })
}

@Composable
fun HelloContent4(name: String, onNameChanged: (String) -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello ${name}!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Name") })
    }
}


class HelloViewModel : ViewModel() {
//    private val _name = MutableLiveData("")
//    val name: LiveData<String> = _name

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

@Composable
fun HelloScreen5(helloViewModel: HelloViewModel = viewModel()) {

    // need implementation "androidx.compose.runtime:runtime-livedata"
//     val name by helloViewModel.name.observeAsState("")

    val name by helloViewModel.name.collectAsState()
    HelloContent4(name, onNameChanged = { helloViewModel.onNameChanged(it) })
}
