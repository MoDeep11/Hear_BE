package modeep.hear.global.document.chat

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import modeep.hear.global.common.response.ApiResult
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatMetaRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateAiImageTaskResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = "Chat", description = "Chat 도메인 관련 API")
interface ChatApiDocument {

    fun createChat(
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateChatResponse>>

    fun finishChat(
        chatId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResult<Unit>>

    fun createMessage(
        chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    fun createVoiceMessage(
        chatId: UUID,
        voice: MultipartFile,
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateMessageResponse>>

    fun uploadImageInChat(
        chatId: UUID,
        files: List<MultipartFile>,
        requests: List<UploadImageInChatMetaRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>>

    fun createAiImageTaskInChat(
        chatId: UUID,
        @RequestBody @Valid
        request: CreateAiImageTaskRequest
    ): ResponseEntity<ApiResult<CreateAiImageTaskResponse>>
}
