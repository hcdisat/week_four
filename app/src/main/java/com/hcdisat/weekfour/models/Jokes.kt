package com.hcdisat.weekfour.models


import com.google.gson.annotations.SerializedName

data class Jokes(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: Joke
)