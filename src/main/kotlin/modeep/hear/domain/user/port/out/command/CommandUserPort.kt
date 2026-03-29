package modeep.hear.domain.user.port.out.command

import modeep.hear.domain.user.model.User
import java.util.UUID

interface CommandUserPort {
    fun save(user: User)

    fun delete(userId: UUID)
}
