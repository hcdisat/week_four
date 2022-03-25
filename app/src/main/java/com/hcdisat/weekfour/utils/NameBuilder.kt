package com.hcdisat.weekfour.utils

import java.lang.Exception

/**
 * Name splitting and validation object
 */
object FullNameBuilder {

    private const val MAX_NAME_SIZE = 2

    fun validate(name: String): JokeCustomName {

        val nameParts = name.split(" ")

        if (nameParts.isEmpty() || nameParts.size != MAX_NAME_SIZE)
            throw Exception("Not valid input")

        return JokeCustomName(nameParts[0], nameParts[1])
    }
}

/**
 * holds a simple first name and last name
 */
data class JokeCustomName(val firstName: String, val lastName: String)