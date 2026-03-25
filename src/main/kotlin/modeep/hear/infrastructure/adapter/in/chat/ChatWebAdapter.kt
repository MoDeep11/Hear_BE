package modeep.hear.infrastructure.adapter.`in`.chat

import jakarta.validation.Valid
import modeep.hear.domain.chat.port.`in`.CompleteChatUseCase
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.`in`.CreateVoiceMessageUseCase
import modeep.hear.domain.chat.port.`in`.GenerateImageInChatUseCase
import modeep.hear.domain.chat.port.`in`.UploadImageInChatUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.chat.ChatApiDocument
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.GenerateImageInChatRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateVoiceMessageResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.GenerateImageInChatResponse
import modeep.hear.infrastructure.adapter.`in`.s3.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.UploadDiaryImageResponse
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
    private val completeChatUseCase: CompleteChatUseCase,
    private val createChatUseCase: CreateChatUseCase,
    private val createMessageUseCase: CreateMessageUseCase,
    private val createVoiceMessageUseCase: CreateVoiceMessageUseCase,
    private val uploadImageInChatUseCase: UploadImageInChatUseCase,
    private val generateImageInChatUseCase: GenerateImageInChatUseCase
) : ChatApiDocument {
    @PostMapping
    override fun createChat(): ResponseEntity<ApiResult<CreateChatResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createChatUseCase.execute()
            )
        )
    }

    @PatchMapping("/{chat_id}")
    override fun completeChat(
        @PathVariable("chat_id") chatId: UUID
    ): ResponseEntity<ApiResult<Unit>> {
        completeChatUseCase.execute(chatId)
        return ResponseEntity.ok(ApiResult())
    }

    @PostMapping("/{chat_id}/messages")
    override fun createMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createMessageUseCase.execute(chatId, request)
            )
        )
    }

    @PostMapping("/{chat_id}/voice")
    override fun createVoiceMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateVoiceMessageRequest
    ): ResponseEntity<ApiResult<CreateVoiceMessageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = createVoiceMessageUseCase.execute(chatId, request)
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
