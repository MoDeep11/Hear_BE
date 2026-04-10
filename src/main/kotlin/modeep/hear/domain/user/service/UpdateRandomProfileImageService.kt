package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.UpdateRandomProfileImageUseCase
import modeep.hear.domain.user.port.out.command.CommandUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.vo.DefaultProfileImageUrl
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UpdateRandomProfileImageService(
    private val securityPort: SecurityPort,
    private val queryUserProfilePort: QueryUserProfilePort,
    private val commandUserProfilePort: CommandUserProfilePort
) : UpdateRandomProfileImageUseCase {
    override fun execute(): UpdateProfileResponse {
        val userId = securityPort.getCurrentUserId()
        val profile = queryUserProfilePort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)

        val imageUrl = DefaultProfileImageUrl.random()
        val updated = profile.updateProfileImageUrl(imageUrl)
        commandUserProfilePort.save(updated)

        return UpdateProfileResponse(
            nickname = updated.nickname,
            profileImageUrl = updated.profileImageUrl,
            updatedAt = LocalDateTime.now()
        )
    }
}
