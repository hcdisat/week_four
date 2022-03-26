package com.hcdisat.weekfour.dataaccess.database

import androidx.room.*
import com.hcdisat.weekfour.models.Joke
import retrofit2.http.DELETE

@Database(entities = [Joke::class], version = DB_VERSION)
abstract class JokesDatabase: RoomDatabase() {
    abstract fun jokesDao()
}

@Dao
interface JokesDao {

    @Query("SELECT * FROM joke")
    suspend fun getAll(): List<Joke>

    @Insert
    suspend fun saveAll(jokes: List<Joke>)

    @DELETE
    suspend fun deleteAll()
}

const val DB_NAME = "jokes_db"
private const val DB_VERSION = 1
