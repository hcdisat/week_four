package com.hcdisat.weekfour.dataaccess.database

import androidx.room.*
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Settings

@Database(
    entities = [Joke::class, Settings::class],
    version = DB_VERSION
)
@TypeConverters(ListToStringConverters::class)
abstract class JokesDatabase: RoomDatabase() {
    abstract fun jokesDao(): JokesDao
    abstract fun settingsDao(): SettingsDao
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

@Dao
interface SettingsDao {
    @Query("SELECT * FROM joke_settings WHERE id = :id")
    suspend fun getSettings(id: Int = SETTINGS_ID): Settings

    @Insert(entity = Settings::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: Settings)

    companion object {
        const val SETTINGS_ID = 1
    }
}

const val DB_NAME = "jokes_db"
private const val DB_VERSION = 1
