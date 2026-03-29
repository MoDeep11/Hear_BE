package modeep.hear.infrastructure.adapter.`in`.chat

import jakarta.validation.Valid
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.`in`.FinishChatUseCase
import modeep.hear.domain.chat.port.`in`.GenerateImageInChatUseCase
import modeep.hear.domain.chat.port.`in`.UploadImageInChatUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.chat.ChatApiDocument
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v1/chats")
class ChatWebAdapter(
    private val finishChatUseCase: FinishChatUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val createMessageUseCase: CreateMessageUseCase,
    private val uploadImageInChatUseCase: UploadImageInChatUseCase,
    private val generateImageInChatUseCase: GenerateImageInChatUseCase
) : ChatApiDocument {
    @PostMapping
    override suspend fun createChat(): ResponseEntity<ApiResult<CreateChatResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createChatUseCase.execute()
            )
        )
    }

    @PatchMapping("/{chat_id}")
    override suspend fun completeChat(
        @PathVariable("chat_id") chatId: UUID
    ): ResponseEntity<ApiResult<Unit>> {
        finishChatUseCase.execute(chatId)
        return ResponseEntity.ok(
            ApiResult(
                status = 202,
                message = "PENDING",
                data = Unit
            )
        )
    }

    @PostMapping("/{chat_id}/messages")
    override suspend fun createMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createMessageUseCase.executeText(chatId, request)
            )
        )
    }

    @PostMapping("/{chat_id}/voice")
    override suspend fun createVoiceMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateVoiceMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createMessageUseCase.executeVoice(chatId, request)
            )
        )
    }

    @PostMapping("/{chat_id}/images")
    override fun uploadImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: List<UploadDiaryImageRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>> {
        return ResponseEntity.ok(
            ApiResult(
                data = uploadImageInChatUseCase.execute(chatId, request)
            )
        )
    }

    @PostMapping("/{chat_id}/messages/generations")
    override fun generateImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody request: GenerateImageInChatRequest
    ): ResponseEntity<ApiResult<GenerateImageInChatResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = generateImageInChatUseCase.execute(chatId, request)
            )
        )
    }
}
