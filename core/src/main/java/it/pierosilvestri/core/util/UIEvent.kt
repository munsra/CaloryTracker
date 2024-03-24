package it.pierosilvestri.core.util

sealed class UIEvent {
    object Success: UIEvent()
    object NavigateUp: UIEvent()

    data class ShowSnackbar(val message: UiText): UIEvent()
}
