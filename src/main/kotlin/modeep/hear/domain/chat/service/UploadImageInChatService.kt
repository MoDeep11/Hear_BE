package modeep.hear.domain.chat.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.chat.port.`in`.UploadImageInChatUseCase
import modeep.hear.domain.chat.port.out.ChatPort
import modeep.hear.domain.s3.port.`in`.UploadImageUseCase
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.UploadDiaryImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UploadImageInChatService(
    private val securityPort: SecurityPort,
    private val chatPort: ChatPort,
    private val uploadImageUseCase: UploadImageUseCase
) : UploadImageInChatUseCase {
    override fun execute(
        chatId: UUID,
        request: List<UploadDiaryImageRequest>
    ): UploadDiaryImageResponse {
        chatPort.findById(chatId).validateOwner(securityPort.getCurrentUser().id)

        val images = uploadImageUseCase.execute(
            requests = request
        )

        TODO("Not yet implemented")
    }
}