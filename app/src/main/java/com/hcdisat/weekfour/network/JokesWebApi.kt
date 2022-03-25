package com.hcdisat.weekfour.network

import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesWebApi {

    @GET(RANDOM_PATH)
    suspend fun getRandom(): Response<Jokes>

    @GET(RANDOM_PATH)
    suspend fun getCustom(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<Jokes>

    companion object {
        const val BASE_PATH = "https://api.icndb.com/"
        private const val RANDOM_PATH = "jokes/random"
    }
}