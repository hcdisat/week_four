package com.hcdisat.weekfour.ui.state

import com.hcdisat.weekfour.models.Settings

sealed class UIState {
    object DEFAULT: UIState()
    object LOADING: UIState()
    class SUCCESS<T>(val response: T): UIState()
    class ERROR(val throwable: Throwable): UIState()
}

sealed class SettingsState {
    object LOADING: SettingsState()
    object DEFAULT: SettingsState()
    class SUCCESS(val settings: Settings? = null): SettingsState()
    object ERROR: SettingsState()
}