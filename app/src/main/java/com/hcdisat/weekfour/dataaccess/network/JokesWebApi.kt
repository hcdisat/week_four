package com.hcdisat.weekfour.dataaccess.network

import com.hcdisat.weekfour.models.JokeList
import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesWebApi {

    @GET(RANDOM_PATH)
    suspend fun getRandom(
        @Query("exclude", encoded = true) excludedCategories: String
    ): Response<Jokes>

    @GET(RANDOM_LIST_PATH)
    suspend fun getRandom(
        @Path("number") number: Int = JOKES_LOAD_SIZE,
        @Query("exclude", encoded = true) excludedCategories: String
    ): Response<JokeList>

    @GET(RANDOM_PATH)
    suspend fun getCustom(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<Jokes>

    companion object {
        // exclude=[nerdy,explicit]
        const val BASE_PATH = "https://api.icndb.com/jokes/"
        const val JOKES_LOAD_SIZE = 20
        private const val RANDOM_PATH = "random"
        private const val RANDOM_LIST_PATH = "random/{number}"
    }
}