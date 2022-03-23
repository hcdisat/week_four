package com.hcdisat.weekfour.ui.state

sealed class RandomState {
    object LOADING: RandomState()
    class SUCCESS<T>(val response: T): RandomState()
    class ERROR(val throwable: Throwable): RandomState()
}
