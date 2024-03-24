package it.pierosilvestri.tracker_presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import it.pierosilvestri.core.util.UIEvent
import it.pierosilvestri.core_ui.LocalSpacing
import it.pierosilvestri.core.R
import it.pierosilvestri.core_ui.CaloryTrackerTheme
import it.pierosilvestri.tracker_domain.model.MealType
import it.pierosilvestri.tracker_domain.model.TrackableFood
import it.pierosilvestri.tracker_presentation.search.components.SearchTextField
import it.pierosilvestri.tracker_presentation.search.components.TrackerFoodItem
import java.time.LocalDate

@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }

                is UIEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }
    _SearchScreen(
        mealName = mealName,
        dayOfMonth = dayOfMonth,
        month = month,
        year = year,
        state = state,
        onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it)) },
        onSearch = { viewModel.onEvent(SearchEvent.OnSearch) },
        onFocusChange = { viewModel.onEvent(SearchEvent.OnSearchFocusChange(it)) },
        onToggleTrackableFood = {
            viewModel.onEvent(
                SearchEvent.OnToggleTrackableFood(it),
            )
        },
        onAmountForFoodChange = { food, amount ->
            viewModel.onEvent(
                SearchEvent.OnAmountForFoodChange(
                    food = food,
                    amount = amount
                )
            )
        },
        onTrackFoodClick = { food, mealtype, date ->
            viewModel.onEvent(
                SearchEvent.OnTrackFoodClick(
                    food = food,
                    mealType = mealtype,
                    date = date
                ),
            )
        }
    )
}

@Composable
private fun _SearchScreen(
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    state: SearchState,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onToggleTrackableFood: (TrackableFood) -> Unit,
    onAmountForFoodChange: (TrackableFood, String) -> Unit,
    onTrackFoodClick: (TrackableFood, MealType, LocalDate) -> Unit
) {
    val spacing = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(
                id = R.string.add_meal,
                mealName
            ),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = {
                onValueChange(it)
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyboardController?.hide()
                onSearch()
            },
            onFocusChanged = {
                onFocusChange(it.isFocused)
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.trackableFood) { food ->
                TrackerFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        onToggleTrackableFood(food.food)
                    },
                    onAmountChange = {
                        onAmountForFoodChange(food.food, it)
                    },
                    onTrack = {
                        keyboardController?.hide()
                        onTrackFoodClick(
                            food.food,
                            MealType.fromString(mealName),
                            LocalDate.of(year, month, dayOfMonth)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFood.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewSearchScreen() {
    val state = SearchState(
        isHintVisible = true
    )
    CaloryTrackerTheme {
        Scaffold {
            _SearchScreen(
                mealName = "Breakfast",
                dayOfMonth = 1,
                month = 1,
                year = 2024,
                state = state,
                onValueChange = {},
                onSearch = {},
                onFocusChange = {},
                onToggleTrackableFood = {},
                onAmountForFoodChange = { food, name -> },
                onTrackFoodClick = { food, mealtype, date -> }
            )
        }
    }

}