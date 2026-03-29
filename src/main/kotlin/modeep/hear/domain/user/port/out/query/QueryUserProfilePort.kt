package modeep.hear.domain.user.port.out.query

import modeep.hear.domain.user.model.UserProfile
import java.util.UUID

interface QueryUserProfilePort {
    fun findByUserId(userId: UUID): UserProfile?
}
