package modeep.hear.infrastructure.adapter.`in`.user.dto.request

data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
