package com.hcdisat.weekfour.network

import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response

interface JokesApiRepositoryContract {
    /**
     * gets a random joke
     */
    suspend fun getRandom(): Response<Jokes>

    /**
     * gets a random joke with a custom name
     */
    suspend fun getCustom(firstName: String, lastName: String): Response<Jokes>
}