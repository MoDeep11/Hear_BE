package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.User

interface CommandUserPort {
    fun save(user: User)
}
