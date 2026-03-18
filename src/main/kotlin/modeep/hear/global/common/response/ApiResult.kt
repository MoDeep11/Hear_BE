package modeep.hear.global.common.response

data class ApiResult<T>(
    val status: Int = 200,
    val message: String = "SUCCESS",
    val data: T? = null
)
