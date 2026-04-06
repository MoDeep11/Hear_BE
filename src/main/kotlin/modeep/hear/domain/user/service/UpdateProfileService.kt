package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.`in`.UpdateProfileUseCase
import modeep.hear.domain.user.port.out.command.CommandUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.domain.user.vo.DefaultProfileImageUrl
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class UpdateProfileService(
    private val queryUserProfilePort: QueryUserProfilePort,
    private val commandUserProfilePort: CommandUserProfilePort,
    private val storagePort: StoragePort,
    private val securityPort: SecurityPort
) : UpdateProfileUseCase {

    private val defaultImageUrls = DefaultProfileImageUrl.entries.map { it.value }.toSet()

    override fun execute(request: UpdateProfileRequest): UpdateProfileResponse {
        val userId = securityPort.getCurrentUserId()
        val profile = queryUserProfilePort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)

        val previousImageUrl = profile.profileImageUrl

        if (profile.nickname == request.nickname && profile.profileImageUrl == request.profileImageUrl) {
            return UpdateProfileResponse(
                nickname = profile.nickname,
                profileImageUrl = profile.profileImageUrl,
                updatedAt = LocalDateTime.now()
            )
        }

        val updatedProfile = profile
            .updateProfileImageUrl(request.profileImageUrl)
            .updateNickname(request.nickname)
        val updatedAt = LocalDateTime.now()
        commandUserProfilePort.save(updatedProfile)

        if (previousImageUrl != updatedProfile.profileImageUrl && previousImageUrl !in defaultImageUrls) {
            storagePort.deleteAll(listOf(previousImageUrl))
        }

        return UpdateProfileResponse(
            nickname = updatedProfile.nickname,
            profileImageUrl = updatedProfile.profileImageUrl,
            updatedAt = updatedAt
        )
    }
}
