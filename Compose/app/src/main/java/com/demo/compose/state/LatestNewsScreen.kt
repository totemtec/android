package com.demo.compose.state

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LatestNewsScreen(viewModel: LatestNewsViewModel = viewModel()) {

    // State of whether more details should be shown
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Some text")
        if (expanded) {
            Text("More details")
        }

        Button(
            // The expand details event is processed by the UI that
            // modifies this composable's internal state.
            onClick = { expanded = !expanded }
        ) {
            val expandText = if (expanded) "Collapse" else "Expand"
            Text("$expandText details")
        }

        // The refresh event is processed by the ViewModel that is in charge
        // of the UI's business logic.
        Button(onClick = { viewModel.refreshNews() }) {
            Text("Refresh data")
        }
    }
}