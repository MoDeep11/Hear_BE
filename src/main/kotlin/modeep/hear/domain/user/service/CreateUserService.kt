package modeep.hear.domain.user.service

import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.User
import modeep.hear.domain.user.model.UserProfile
import modeep.hear.domain.user.model.UserStat
import modeep.hear.domain.user.port.`in`.CreateUserUseCase
import modeep.hear.domain.user.port.out.UserPort
import modeep.hear.domain.user.port.out.UserProfilePort
import modeep.hear.domain.user.port.out.UserStatPort
import modeep.hear.global.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateUserService(
    private val userPort: UserPort,
    private val userStatPort: UserStatPort,
    private val userProfilePort: UserProfilePort
) : CreateUserUseCase {
    override fun execute(user: User) {
        if (userPort.existsByEmail(user.email)) {
            throw BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS)
        }

        val userStat = UserStat.create(user.id)
        val userProfile = UserProfile.create(user.id, null)

        userPort.save(user)
        userStatPort.save(userStat)
        userProfilePort.save(userProfile)
    }
}
