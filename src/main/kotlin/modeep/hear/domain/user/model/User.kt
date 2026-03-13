package modeep.hear.domain.user.model

import modeep.hear.domain.common.annotation.Aggregate
import modeep.hear.domain.user.type.Role
import modeep.hear.domain.user.type.UserStatus
import java.time.LocalDateTime
import java.util.UUID

@Aggregate
data class User(
    val id: UUID? = null,
    val email: String,
    val password: String,
    val role: Role,
    val status: UserStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isEmailSubscribed: Boolean,
)