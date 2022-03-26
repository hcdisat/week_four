package com.hcdisat.weekfour.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.weekfour.exceptioons.EmptyResponseException
import com.hcdisat.weekfour.exceptioons.InvalidFullNameException
import com.hcdisat.weekfour.exceptioons.ServerErrorResponseException
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Jokes
import com.hcdisat.weekfour.dataaccess.network.EndPoints
import com.hcdisat.weekfour.dataaccess.network.JokesWebApi
import com.hcdisat.weekfour.ui.state.UIState
import com.hcdisat.weekfour.utils.FullNameBuilder
import com.hcdisat.weekfour.utils.JokeCustomName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class JokesViewModel(
    private val api: JokesWebApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    /**
     * liveData to observe and get data
     */
    private val _randomJoke: MutableLiveData<UIState> =
        MutableLiveData(UIState.DEFAULT)
    val randomJoke: LiveData<UIState> get() = _randomJoke

    /**
     * selected [Joke] used by different fragments
     */
    lateinit var selectedJoke: Joke

    /**
     * [EndPoints] represents which endpoint it's going too be trigger
     */
    var endPoint: EndPoints = EndPoints.RANDOM

    /**
     * load jokes from the web api
     */
    fun getJoke(args: String = "") {
        _randomJoke.value = UIState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {

                val response = when(endPoint) {
                    EndPoints.RANDOM_LIST -> api.getRandom(JokesWebApi.JOKES_LOAD_SIZE)
                    else -> requestJoke(args)
                }

                if (response.isSuccessful) {
                    response.body()?.let {
                        _randomJoke.postValue(UIState.SUCCESS(it))
                    } ?: throw EmptyResponseException()
                } else {
                    throw ServerErrorResponseException()
                }
            } catch (e: Exception) {
                _randomJoke.postValue(UIState.ERROR(e))
            }
        }
    }

    /**
     * resets UI state to its default settings
     */
    fun resetUIState() {
        _randomJoke.value = UIState.DEFAULT
    }

    /**
     * request custom joke from api
     */
    private suspend fun getCustomJoke(fullNameString: String): Response<Jokes> {
        var fullName = JokeCustomName("", "")
        try {
            fullName = FullNameBuilder.validate(fullNameString)
        } catch (e: InvalidFullNameException) {
            _randomJoke.value = UIState.ERROR(e)
        }

        return api.getCustom(fullName.firstName, fullName.lastName)
    }


    /**
     * based on selected endpoint return the right suspended method
     */
    private suspend fun requestJoke(fullName: String = ""): Response<Jokes> {
        return when (endPoint) {
            EndPoints.RANDOM -> api.getRandom()
            EndPoints.CUSTOM -> getCustomJoke(fullName)
            else -> api.getRandom()
        }
    }
}