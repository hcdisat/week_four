package com.hcdisat.weekfour.dataaccess.network

import com.hcdisat.weekfour.dataaccess.database.SettingsDatabaseRepositoryContract
import com.hcdisat.weekfour.models.JokeList
import com.hcdisat.weekfour.models.Jokes
import retrofit2.Response

class JokesApiRepository(
    private val jokesApi: JokesWebApi,
): JokesApiRepositoryContract {

    override suspend fun getRandom(excludedCategories: String): Response<Jokes> =
        jokesApi.getRandom("[$excludedCategories]")

    override suspend fun getRandom(number: Int, excludedCategories: String) =
        jokesApi.getRandom(number, "[$excludedCategories]")

    override suspend fun getCustom(firstName: String, lastName: String) =
        jokesApi.getCustom(firstName, lastName)
}