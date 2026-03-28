package modeep.hear.domain.user.port.out.query

import modeep.hear.domain.user.model.User
import java.util.UUID

interface QueryUserPort {
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun findById(id: UUID): User?
}
