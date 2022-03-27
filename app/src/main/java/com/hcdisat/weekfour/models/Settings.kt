package com.hcdisat.weekfour.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke_settings")
data class Settings(
    @PrimaryKey val id: Int,
    val showExplicitContent: Boolean
)
