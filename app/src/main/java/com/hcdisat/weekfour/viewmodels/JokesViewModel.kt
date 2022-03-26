package com.hcdisat.weekfour.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.weekfour.dataaccess.database.DatabaseRepositoryContract
import com.hcdisat.weekfour.exceptioons.EmptyResponseException
import com.hcdisat.weekfour.exceptioons.InvalidFullNameException
import com.hcdisat.weekfour.exceptioons.ServerErrorResponseException
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Jokes
import com.hcdisat.weekfour.dataaccess.network.EndPoints
import com.hcdisat.weekfour.dataaccess.network.JokesApiRepositoryContract
import com.hcdisat.weekfour.dataaccess.network.JokesWebApi
import com.hcdisat.weekfour.ui.state.UIState
import com.hcdisat.weekfour.utils.FullNameBuilder
import com.hcdisat.weekfour.utils.JokeCustomName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

class JokesViewModel(
    private val apiRepository: JokesApiRepositoryContract,
    private val databaseRepository: DatabaseRepositoryContract,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    /**
     * liveData to observe and get data
     */
    private val _singleJokeState: MutableLiveData<UIState> =
        MutableLiveData(UIState.DEFAULT)
    val state: LiveData<UIState> get() = _singleJokeState

    /**
     * liveData to observe and get data
     */
    private val _jokesState: MutableLiveData<UIState> =
        MutableLiveData(UIState.DEFAULT)
    val jokesState: LiveData<UIState> get() = _jokesState

    /**
     * selected [Joke] used by different fragments
     */
    lateinit var selectedJoke: Joke

    /**
     * selected [List<Joke>] used by different fragments
     */
    lateinit var jokes: List<Joke>

    /**
     * [EndPoints] represents which endpoint it's going too be trigger
     */
    var endPoint: EndPoints = EndPoints.RANDOM

    /**
     * load a single joke from the web api
     */
    fun getJoke(args: String = "") {
        _singleJokeState.value = UIState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = requestJoke(args)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _singleJokeState.postValue(UIState.SUCCESS(it))
                    } ?: throw EmptyResponseException()
                } else {
                    throw ServerErrorResponseException()
                }
            } catch (e: Exception) {
                _singleJokeState.postValue(UIState.ERROR(e))
            }
        }
    }

    /**
     * load a list of jokes from the web api
     */
    fun getJokes() {
        _jokesState.value = UIState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = apiRepository.getRandom(JokesWebApi.JOKES_LOAD_SIZE)
                if (response.isSuccessful) {
                    response.body()?.let {
                        databaseRepository.deleteAll()
                        databaseRepository.saveAll(it.value)
                        _jokesState.postValue(UIState.SUCCESS(databaseRepository.getAll()))
                    }?: throw EmptyResponseException()

                    return@launch
                }

                throw ServerErrorResponseException()
            }
            // no internet connection, here I'm loading what I have in DB
            catch (noConnectionException: UnknownHostException) {
                _jokesState.postValue(UIState.SUCCESS(databaseRepository.getAll()))
            }
            catch (e: Exception) {
                _jokesState.postValue(UIState.ERROR(e))
            }
        }
    }

    /**
     * resets UI state to its default settings
     */
    fun resetUIState() {
        _singleJokeState.value = UIState.DEFAULT
        _jokesState.value = UIState.DEFAULT
    }

    /**
     * request custom joke from api
     */
    private suspend fun getCustomJoke(fullNameString: String): Response<Jokes> {
        var fullName = JokeCustomName("", "")
        try {
            fullName = FullNameBuilder.validate(fullNameString)
        } catch (e: InvalidFullNameException) {
            _singleJokeState.value = UIState.ERROR(e)
        }

        return apiRepository.getCustom(fullName.firstName, fullName.lastName)
    }


    /**
     * based on selected endpoint return the right suspended method
     */
    private suspend fun requestJoke(fullName: String = ""): Response<Jokes> {
        return when (endPoint) {
            EndPoints.RANDOM -> apiRepository.getRandom()
            EndPoints.CUSTOM -> getCustomJoke(fullName)
            else -> apiRepository.getRandom()
        }
    }
}