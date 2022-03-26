package com.hcdisat.weekfour.dataaccess.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListToStringConverters {
    @TypeConverter
    fun fromList(stringList: List<String>): String  = stringList.joinToString()

    @TypeConverter
    fun fromString(string: String): List<String> =
        string.split(",").toList()
}