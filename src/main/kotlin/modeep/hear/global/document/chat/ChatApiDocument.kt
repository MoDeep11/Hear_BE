package modeep.hear.global.document.chat

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateAiImageTaskResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import modeep.hear.infrastructure.security.userdetails.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "Chat", description = "Chat 도메인 관련 API")
interface ChatApiDocument {

    suspend fun createChat(
        user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateChatResponse>>

    suspend fun finishChat(
        chatId: UUID,
        user: CustomUserDetails
    ): ResponseEntity<ApiResult<Unit>>

    suspend fun createMessage(
        chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest,
        user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    suspend fun createVoiceMessage(
        chatId: UUID,
        @RequestBody @Valid
        request: CreateVoiceMessageRequest,
        user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    fun uploadImageInChat(
        chatId: UUID,
        @RequestBody @Valid
        request: List<UploadDiaryImageRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>>

    fun createAiImageTaskInChat(
        chatId: UUID,
        @RequestBody @Valid
        request: CreateAiImageTaskRequest
    ): ResponseEntity<ApiResult<CreateAiImageTaskResponse>>
}
