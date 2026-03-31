package modeep.hear.domain.auth.port.out

import modeep.hear.domain.user.model.User
import java.util.UUID

interface SecurityPort {
    fun getCurrentUser(): User

    fun getCurrentUserId(): UUID
}
