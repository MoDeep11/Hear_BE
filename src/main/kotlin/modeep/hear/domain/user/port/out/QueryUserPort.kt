package modeep.hear.domain.user.port.out

import modeep.hear.domain.user.model.User

interface QueryUserPort {
    fun findByEmail(email: String): User
}
