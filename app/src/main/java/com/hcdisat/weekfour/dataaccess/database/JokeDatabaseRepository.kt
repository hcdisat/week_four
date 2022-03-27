package com.hcdisat.weekfour.dataaccess.database

import com.hcdisat.weekfour.models.Joke

/**
 * [JokeDatabaseRepositoryContract] used to interface database operations
 */
interface JokeDatabaseRepositoryContract {
    suspend fun getAll(): List<Joke>
    suspend fun saveAll(jokes: List<Joke>)
    suspend fun deleteAll()
}

/**
 * [JokeDatabaseRepository] implementing [JokeDatabaseRepositoryContract]
 * used as repository layer to abstract database operations
 */
class JokeDatabaseRepository(
    private val jokesDao: JokesDao
) : JokeDatabaseRepositoryContract {
    override suspend fun getAll(): List<Joke> = jokesDao.getAll()
    override suspend fun saveAll(jokes: List<Joke>) = jokesDao.saveAll(jokes)
    override suspend fun deleteAll() = jokesDao.deleteAll()
}