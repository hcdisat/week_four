package com.hcdisat.weekfour.exceptioons

class EmptyResponseException(
): Exception("No jokes found in server. Please try again later") {
    val code = 600
}

class ServerErrorResponseException(
): Exception("Server not available. Please try again later") {
    val code = 600
}