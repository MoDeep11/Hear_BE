package modeep.hear.domain.user.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.user.port.`in`.GetRandomProfileImageUseCase
import modeep.hear.domain.user.vo.DefaultProfileImageUrl
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetRandomProfileImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetRandomProfileImageService(
    private val securityPort: SecurityPort
) : GetRandomProfileImageUseCase {
    override fun execute(): GetRandomProfileImageResponse {
        securityPort.getCurrentUser()
        val imageUrl = DefaultProfileImageUrl.random()
        return GetRandomProfileImageResponse(imageUrl)
    }
}
