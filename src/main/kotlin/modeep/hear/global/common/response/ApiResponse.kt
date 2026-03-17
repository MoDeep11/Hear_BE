package modeep.hear.global.common.response

@Deprecated("Not used anymore")
data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T? = null
)
