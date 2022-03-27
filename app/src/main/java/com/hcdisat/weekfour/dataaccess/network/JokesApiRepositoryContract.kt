package com.hcdisat.weekfour.dataaccess.network

import com.hcdisat.weekfour.models.JokeList
import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response

interface JokesApiRepositoryContract {
    /**
     * gets a random joke
     */
    suspend fun getRandom(excludedCategories: String = ""): Response<Jokes>

    /**
     * gets the list of jokes
     */
    suspend fun getRandom(
        number: Int = JokesWebApi.JOKES_LOAD_SIZE,
        excludedCategories: String = ""
    ): Response<JokeList>

    /**
     * gets a random joke with a custom name
     */
    suspend fun getCustom(firstName: String, lastName: String): Response<Jokes>
}