package com.hcdisat.weekfour.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Joke(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("joke")
    val joke: String
)