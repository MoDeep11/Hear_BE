package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.domain.storage.vo.ServiceType
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.model.UserProfile
import modeep.hear.domain.user.port.`in`.UpdateProfileUseCase
import modeep.hear.domain.user.port.out.command.CommandUserProfilePort
import modeep.hear.domain.user.port.out.query.QueryUserProfilePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
@Transactional
class UpdateProfileService(
    private val queryUserProfilePort: QueryUserProfilePort,
    private val commandUserProfilePort: CommandUserProfilePort,
    private val storagePort: StoragePort,
    private val securityPort: SecurityPort
) : UpdateProfileUseCase {
    override fun execute(
        request: UpdateProfileRequest,
        image: MultipartFile?
    ): UpdateProfileResponse {
        val userId = securityPort.getCurrentUserId()
        var profile = queryUserProfilePort.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_PROFILE_NOT_FOUND)

        val isSameNick = profile.nickname == request.nickname
        if (isSameNick && image == null) {
            return UpdateProfileResponse(
                nickname = profile.nickname,
                profileImageUrl = profile.profileImageUrl,
                updatedAt = profile.baseTime.updatedAt
            )
        }

        val previousImageUrl = profile.profileImageUrl

        if (!isSameNick) {
            profile = profile.updateNickname(request.nickname)
        }

        if (image != null) {
            val uploadedUrl = uploadFile(image, profile)
            profile = profile.updateProfileImageUrl(uploadedUrl)
        }

        commandUserProfilePort.save(profile)

        if (image != null) {
            storagePort.deleteAll(listOf(previousImageUrl))
        }

        return UpdateProfileResponse(
            nickname = profile.nickname,
            profileImageUrl = profile.profileImageUrl,
            updatedAt = LocalDateTime.now()
        )
    }

    private fun uploadFile(image: MultipartFile, profile: UserProfile): String {
        val fileData = FileData(
            fileName = image.originalFilename,
            contentType = image.contentType,
            size = image.size,
            type = ServiceType.PROFILE,
            userId = profile.userId
        )

        return storagePort.upload(image, fileData)
    }
}
