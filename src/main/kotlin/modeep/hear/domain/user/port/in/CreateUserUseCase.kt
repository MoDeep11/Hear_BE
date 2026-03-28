package modeep.hear.domain.user.port.`in`

import modeep.hear.domain.user.model.User

interface CreateUserUseCase {
    fun execute(user: User)
}
