package com.hcdisat.weekfour.dataaccess.database

import androidx.room.*
import com.hcdisat.weekfour.models.Joke

@Database(entities = [Joke::class], version = DB_VERSION)
@TypeConverters(ListToStringConverters::class)
abstract class JokesDatabase: RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}

@Dao
interface JokesDao {

    @Query("SELECT * FROM joke")
    suspend fun getAll(): List<Joke>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(jokes: List<Joke>)

    @Query("DELETE FROM joke")
    suspend fun deleteAll()
}

const val DB_NAME = "jokes_db"
private const val DB_VERSION = 1
