package com.hcdisat.weekfour.dataaccess.database

import com.hcdisat.weekfour.models.Joke

/**
 * [DatabaseRepositoryContract] used to interface database operations
 */
interface DatabaseRepositoryContract {
    suspend fun getAll(): List<Joke>
    suspend fun saveAll(jokes: List<Joke>)
    suspend fun deleteAll()
}

/**
 * [DatabaseRepository] implementing [DatabaseRepositoryContract]
 * used as repository layer to abstract database operations
 */
class DatabaseRepository(
    private val jokesDao: JokesDao
) : DatabaseRepositoryContract {
    override suspend fun getAll(): List<Joke> = jokesDao.getAll()
    override suspend fun saveAll(jokes: List<Joke>) = jokesDao.saveAll(jokes)
    override suspend fun deleteAll() = jokesDao.deleteAll()
}