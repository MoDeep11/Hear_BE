package modeep.hear.domain.user.port.out.command

import modeep.hear.domain.user.model.UserProfile
import java.util.UUID

interface CommandUserProfilePort {
    fun save(userProfile: UserProfile)

    fun delete(userId: UUID)
}
