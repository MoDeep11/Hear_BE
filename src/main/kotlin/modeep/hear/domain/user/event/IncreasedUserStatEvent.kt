package modeep.hear.domain.user.event

import java.util.UUID

data class IncreasedUserStatEvent(
    val userId: UUID
)
