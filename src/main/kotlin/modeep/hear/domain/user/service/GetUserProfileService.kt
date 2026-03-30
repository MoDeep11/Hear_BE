package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.GetUserProfileUseCase
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetUserProfileService(
    private val queryUserProfilePort: QueryUserProfilePort,
    private val securityPort: SecurityPort
) : GetUserProfileUseCase {
    override fun execute(): UserProfileResponse {
        val user = securityPort.getCurrentUser()
        val profile = queryUserProfilePort.findByUserId(user.id)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)

        return UserProfileResponse(
            userId = user.id,
            email = user.email,
            nickname = profile.nickname,
            profileImageUrl = profile.profileImageUrl,
            createdAt = profile.baseTime.createdAt,
            updatedAt = profile.baseTime.updatedAt,
        )
    }
}