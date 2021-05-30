package miq0717.dailyislamcodesession.networkmodel

data class ErrorResponse(val error: Error)

data class Error(
    val code: Int,
    val details: String
)
