package it.pierosilvestri.onboarding_presentation.height

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
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import it.pierosilvestri.core.R
import it.pierosilvestri.core.domain.model.Gender
import it.pierosilvestri.core.util.UIEvent
import it.pierosilvestri.core_ui.LocalSpacing
import it.pierosilvestri.onboarding_presentation.components.ActionButton
import it.pierosilvestri.onboarding_presentation.components.SelectableButton
import it.pierosilvestri.onboarding_presentation.components.UnitTextField

@Composable
fun HeightScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true,){
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UIEvent.Success -> onNextClick()
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
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
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )
            Spacer(
                modifier = Modifier.height(spacing.spaceMedium)
            )
            UnitTextField(value = viewModel.height, onValueChange = viewModel::onHeightEnter, unit = stringResource(
                id = R.string.cm
            ))
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}