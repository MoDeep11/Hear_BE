package modeep.hear.domain.user.port.out.query

import modeep.hear.domain.user.model.UserStat
import java.util.UUID

interface QueryUserStatPort {
    fun findByUserId(userId: UUID): UserStat?
}