package com.hcdisat.weekfour.models

import com.google.gson.annotations.SerializedName

data class JokeList(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: List<Joke>
)