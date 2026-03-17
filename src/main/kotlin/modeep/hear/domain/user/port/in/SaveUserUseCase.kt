package modeep.hear.domain.user.port.`in`

import modeep.hear.domain.user.model.User

interface SaveUserUseCase {
    fun execute(user: User)
}
