package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diceroller.ui.theme.DiceRollerTheme

class DiceRollerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRoller()
            }
        }
    }
}

/**
 * From
 * https://developer.android.com/topic/libraries/architecture/viewmodel#jetpack-compose_1
 */

@Composable
fun DiceRollerScreen(
    modifier: Modifier = Modifier,
    viewModel: DiceRollViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dice1ImageId = imageId(uiState.firstDiceValue)
    val dice2ImageId = imageId(uiState.secondDiceValue)

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text("Rolls Count: ${uiState.numberOfRolls}")

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(dice1ImageId),
            contentDescription = "dice#1",
            Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(dice2ImageId),
            contentDescription = "dice#2",
            Modifier.size(160.dp)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.rollDice() }) {
            Text(stringResource(R.string.roll))
        }
    }
}

fun imageId(index: Int?): Int {
    return when (index) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview
@Composable
fun DiceRoller() {
    DiceRollerScreen()
}