package com.hcdisat.weekfour.exceptioons

/**
 * [Exception] thrown when the http response is empty
 */
class EmptyResponseException(
): Exception("No jokes found in server. Please try again later") {
    val code = 600
}

/**
 * [Exception] thrown where there's a server error during http request
 */
class ServerErrorResponseException(
): Exception("Server not available. Please try again later") {
    val code = 601
}

/**
 * [Exception]] thrown when an invalid full name is provided
 */
class InvalidFullNameException(): Exception("Provided name is not valid") {
    val code = 700
}