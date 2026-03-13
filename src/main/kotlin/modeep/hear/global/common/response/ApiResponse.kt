package modeep.hear.global.common.response

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T? = null
)
