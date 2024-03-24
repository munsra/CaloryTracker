package it.pierosilvestri.tracker_presentation.tracker_overview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import it.pierosilvestri.core.util.UIEvent
import it.pierosilvestri.core_ui.CaloryTrackerTheme
import it.pierosilvestri.core_ui.LocalSpacing
import it.pierosilvestri.tracker_domain.model.TrackedFood
import it.pierosilvestri.core.R
import it.pierosilvestri.tracker_presentation.tracker_overview.components.AddButton
import it.pierosilvestri.tracker_presentation.tracker_overview.components.DaySelector
import it.pierosilvestri.tracker_presentation.tracker_overview.components.ExpandableMeal
import it.pierosilvestri.tracker_presentation.tracker_overview.components.NutrientsHeader
import it.pierosilvestri.tracker_presentation.tracker_overview.components.TrackedFoodItem
import kotlinx.coroutines.flow.collect

@Composable
fun TrackerOverviewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val state = viewModel.state

    _TrackerOverViewScreen(
        state = state,
        onPreviousDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick) },
        onNextDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick) },
        onToggleClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(it)) },
        onDeleteTackedFood = { viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(it)) },
        onAddFoodClick = { mealName, dayOfMonth, month, year ->
            onNavigateToSearch(mealName, dayOfMonth, month, year)
        }
    )
}

@Composable
private fun _TrackerOverViewScreen(
    state: TrackerOverviewState,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    onToggleClick: (meal: Meal) -> Unit,
    onDeleteTackedFood: (food: TrackedFood) -> Unit,
    onAddFoodClick: (String, Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPrevioudDayClick = onPreviousDayClick,
                onNextDayClick = onNextDayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = { onToggleClick(meal) },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        val foods = state.trackedFoods.filter {
                            it.mealType == meal.mealType
                        }
                        foods.forEach { food ->
                            TrackedFoodItem(
                                trackedFoodItem = food,
                                onDeleteClick = {
                                    onDeleteTackedFood(food)
                                }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                        AddButton(
                            text = stringResource(
                                id = R.string.add_meal,
                                meal.name.asString(context)
                            ),
                            onClick = {
                                onAddFoodClick(
                                    meal.name.asString(context),
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year,
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewTrackerOverviewScreen() {
    CaloryTrackerTheme {
        Scaffold {
            val state = TrackerOverviewState()
            _TrackerOverViewScreen(
                state = state,
                onNextDayClick = {},
                onPreviousDayClick = {},
                onToggleClick = {},
                onDeleteTackedFood = {},
                onAddFoodClick = {mealName, dayOfMonth, month, year -> }
            )
        }
    }

}
