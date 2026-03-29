package modeep.hear.global.document.chat

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateAiImageTaskResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Chat", description = "Chat 도메인 관련 API")
interface ChatApiDocument {

    suspend fun createChat(): ResponseEntity<ApiResult<CreateChatResponse>>

    suspend fun completeChat(
        chatId: UUID
    ): ResponseEntity<ApiResult<Unit>>

    suspend fun createMessage(
        chatId: UUID,
        request: CreateMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    suspend fun createVoiceMessage(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    fun uploadImageInChat(
        chatId: UUID,
        request: List<UploadDiaryImageRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>>

    fun createAiImageTaskInChat(
        chatId: UUID,
        request: CreateAiImageTaskRequest
    ): ResponseEntity<ApiResult<CreateAiImageTaskResponse>>
}
