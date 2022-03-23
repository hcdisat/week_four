package com.hcdisat.weekfour.models


import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("joke")
    val joke: String
)