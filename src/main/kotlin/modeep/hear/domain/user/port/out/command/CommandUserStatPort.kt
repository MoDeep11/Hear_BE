package modeep.hear.domain.user.port.out.command

import modeep.hear.domain.user.model.UserStat
import java.util.UUID

interface CommandUserStatPort {
    fun save(userStat: UserStat)

    fun delete(userId: UUID)
}
