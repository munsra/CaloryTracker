package it.pierosilvestri.tracker_presentation.search

import it.pierosilvestri.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)
