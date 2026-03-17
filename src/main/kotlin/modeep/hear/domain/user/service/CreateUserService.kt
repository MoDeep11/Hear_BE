package modeep.hear.domain.user.service

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.port.`in`.CreateUserUseCase
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userPort: UserPort
) : CreateUserUseCase {
    @Transactional
    override fun execute(user: User) {
        if (userPort.existsByEmail(user.email)) {
            throw BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS)
        }
        userPort.save(user)
    }
}
