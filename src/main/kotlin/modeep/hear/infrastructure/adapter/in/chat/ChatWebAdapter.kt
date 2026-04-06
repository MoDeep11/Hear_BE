package modeep.hear.infrastructure.adapter.`in`.chat

import jakarta.validation.Valid
import modeep.hear.domain.chat.port.`in`.CreateAiImageTaskInChatUseCase
import modeep.hear.domain.chat.port.`in`.CreateChatUseCase
import modeep.hear.domain.chat.port.`in`.CreateMessageUseCase
import modeep.hear.domain.chat.port.`in`.FinishChatUseCase
import modeep.hear.domain.chat.port.`in`.UploadImageInChatUseCase
import modeep.hear.domain.chat.vo.AiImageTaskStatus
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.chat.ChatApiDocument
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateAiImageTaskRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.CreateVoiceMessageRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateAiImageTaskResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.request.UploadDiaryImageRequest
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import modeep.hear.infrastructure.security.userdetails.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
    private val createAiImageTaskInChatUseCase: CreateAiImageTaskInChatUseCase
) : ChatApiDocument {
    @PostMapping
    override suspend fun createChat(
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateChatResponse>> {
        return ResponseEntity.ok(ApiResult(
            data = createChatUseCase.execute(user.getUser())
        ))
    }

    @PatchMapping("/{chat_id}")
    override suspend fun finishChat(
        @PathVariable("chat_id") chatId: UUID,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<ApiResult<Unit>> {
        finishChatUseCase.execute(chatId, user.getUser())
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(ApiResult(
                status = 202,
                message = AiImageTaskStatus.RESERVED.name,
                data = Unit
            ))
    }

    @PostMapping("/{chat_id}/messages")
    override suspend fun createMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        return ResponseEntity.ok(ApiResult(
            data = createMessageUseCase.executeText(chatId, request, user.getUser())
        ))
    }

    @PostMapping("/{chat_id}/voice")
    override suspend fun createVoiceMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateVoiceMessageRequest,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        return ResponseEntity.ok(ApiResult(
            data = createMessageUseCase.executeVoice(chatId, request, user.getUser())
        ))
    }

    @PostMapping("/{chat_id}/images")
    override fun uploadImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: List<UploadDiaryImageRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>> {
        return ResponseEntity.ok(ApiResult(
            data = uploadImageInChatUseCase.execute(chatId, request)
        ))
    }

    @PostMapping("/{chat_id}/images/generations")
    override fun createAiImageTaskInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateAiImageTaskRequest
    ): ResponseEntity<ApiResult<CreateAiImageTaskResponse>> {
        val res = createAiImageTaskInChatUseCase.execute(chatId, request)
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(ApiResult(
                data = CreateAiImageTaskResponse.from(res),
                status = 202,
                message = res.status.name
            ))
    }
}
