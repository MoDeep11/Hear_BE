package modeep.hear.domain.auth.port.out

import modeep.hear.domain.user.model.User

interface SecurityPort {

    fun getCurrentUser(): User
}