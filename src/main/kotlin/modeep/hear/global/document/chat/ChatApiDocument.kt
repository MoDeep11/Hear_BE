package modeep.hear.global.document.chat

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateVoiceMessageResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.UploadImageInChatResponse
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Chat", description = "Chat 도메인 관련 API")
interface ChatApiDocument {

    fun createChat(): ResponseEntity<ApiResult<CreateChatResponse>>

    fun deleteChat(
        chatId: UUID
    ): ResponseEntity<ApiResult<Unit>>

    fun createMessage(
        chatId: UUID,
        request: CreateMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    fun createVoiceMessage(
        chatId: UUID,
        request: CreateVoiceMessageRequest
    ): ResponseEntity<ApiResult<CreateVoiceMessageResponse>>

    fun uploadImageInChat(
        chatId: UUID,
        request: UploadImageInChatRequest
    ): ResponseEntity<ApiResult<UploadImageInChatResponse>>

    fun generateImageInChat(
        chatId: UUID,
        request: GenerateImageInChatRequest
    ): ResponseEntity<ApiResult<GenerateImageInChatResponse>>
}
