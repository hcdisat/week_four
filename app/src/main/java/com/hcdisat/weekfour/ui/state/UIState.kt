package com.hcdisat.weekfour.ui.state

sealed class UIState {
    object DEFAULT: UIState()
    object LOADING: UIState()
    class SUCCESS<T>(val response: T): UIState()
    class ERROR(val throwable: Throwable): UIState()
}
