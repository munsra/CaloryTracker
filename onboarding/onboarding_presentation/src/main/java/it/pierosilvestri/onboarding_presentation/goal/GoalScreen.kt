package it.pierosilvestri.onboarding_presentation.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import it.pierosilvestri.core.util.UIEvent
import it.pierosilvestri.core_ui.LocalSpacing
import it.pierosilvestri.core.R
import it.pierosilvestri.core.domain.model.ActivityLevel
import it.pierosilvestri.core.domain.model.Gender
import it.pierosilvestri.core.domain.model.GoalType
import it.pierosilvestri.onboarding_presentation.components.ActionButton
import it.pierosilvestri.onboarding_presentation.components.SelectableButton

@Composable
fun GoalScreen(
    onNextClick: () -> Unit,
    viewModel: GoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true,){
        viewModel.uiEvent.collect {
            when(it) {
                is UIEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.lose_keep_or_gain_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(
                modifier = Modifier.height(spacing.spaceMedium)
            )
            Row {
                SelectableButton(
                    text = stringResource(id = R.string.lose),
                    isSelected = viewModel.selectedGoal is GoalType.LoseWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeClick(GoalType.LoseWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.keep),
                    isSelected = viewModel.selectedGoal is GoalType.KeepWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeClick(GoalType.KeepWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                SelectableButton(
                    text = stringResource(id = R.string.gain),
                    isSelected = viewModel.selectedGoal is GoalType.GainWeight,
                    color = MaterialTheme.colors.primaryVariant,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGoalTypeClick(GoalType.GainWeight)
                    },
                    textStyle = MaterialTheme.typography.button.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
            )
    }
}

@Preview
@Composable
fun PreviewGenderScreen() {

}