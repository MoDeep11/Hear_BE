package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.UpdateNicknameUserUseCase
import modeep.hear.domain.user.port.out.UserProfilePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateNicknameRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateNicknameResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UpdateNicknameUserService(
    private val userProfilePort: UserProfilePort,
    private val securityPort: SecurityPort
) : UpdateNicknameUserUseCase {
    override fun execute(request: UpdateNicknameRequest): UpdateNicknameResponse {
        val user = securityPort.getCurrentUser()
        val profile = userProfilePort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)
        profile.nickname = request.nickname
        val updatedAt = LocalDateTime.now()
        userProfilePort.save(profile)

        return UpdateNicknameResponse(
            nickname = profile.nickname,
            updatedAt = updatedAt
        )
    }
}
