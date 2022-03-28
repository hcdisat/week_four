package com.hcdisat.weekfour.dataaccess.database

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class SetUpConfigurationCallBack: RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        runBlocking {
            val values = ContentValues().apply {
                put("id", 1)
                put("showExplicitContent", false)
            }
            db.insert("joke_settings", OnConflictStrategy.REPLACE, values)
            values.clear()
        }
    }
}