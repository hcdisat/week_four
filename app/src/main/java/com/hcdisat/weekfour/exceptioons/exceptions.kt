package com.hcdisat.weekfour.exceptioons

/**
 * [Exception] thrown when the http response is empty
 */
class EmptyResponseException(
): Exception("No jokes found in server. Please try again later")

/**
 * [Exception] thrown where there's a server error during http request
 */
class ServerErrorResponseException(
): Exception("Server not available. Please try again later")

/**
 * [Exception]] thrown when an invalid full name is provided
 */
class InvalidFullNameException(): Exception("Provided name is not valid")