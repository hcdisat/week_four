package com.hcdisat.weekfour.dataaccess.network

import com.hcdisat.weekfour.models.JokeList
import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response

class JokesApiRepository(
    private val jokesApi: JokesWebApi
): JokesApiRepositoryContract {

    override suspend fun getRandom() = jokesApi.getRandom()

    override suspend fun getRandom(number: Int) = jokesApi.getRandom(number)

    override suspend fun getCustom(firstName: String, lastName: String) =
        jokesApi.getCustom(firstName, lastName)
}