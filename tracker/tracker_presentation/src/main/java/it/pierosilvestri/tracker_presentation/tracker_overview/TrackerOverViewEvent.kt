package it.pierosilvestri.tracker_presentation.tracker_overview

import it.pierosilvestri.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {

    object OnNextDayClick: TrackerOverviewEvent()
    object OnPreviousDayClick: TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood): TrackerOverviewEvent()
    // data class OnAddFoodClick(val meal: Meal): TrackerOverviewEvent()

}