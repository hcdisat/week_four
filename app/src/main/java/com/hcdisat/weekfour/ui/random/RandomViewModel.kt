package com.hcdisat.weekfour.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.weekfour.exceptioons.EmptyResponseException
import com.hcdisat.weekfour.exceptioons.ServerErrorResponseException
import com.hcdisat.weekfour.network.JokesWebApi
import com.hcdisat.weekfour.ui.state.RandomState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RandomViewModel(
    private val api: JokesWebApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    /**
     * liveData to observe and get data
     */
    private val _randomJoke: MutableLiveData<RandomState> =
        MutableLiveData(RandomState.LOADING)
    val randomJoke: LiveData<RandomState> get() = _randomJoke

    fun getJoke() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = api.getRandom()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _randomJoke.postValue(RandomState.SUCCESS(it.value))
                    } ?: throw EmptyResponseException()
                } else {
                    throw ServerErrorResponseException()
                }
            } catch (e: Exception) {
                _randomJoke.postValue(RandomState.ERROR(e))
            }
        }
    }
}