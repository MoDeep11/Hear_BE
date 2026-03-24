package modeep.hear.infrastructure.adapter.`in`.chat

import jakarta.validation.Valid
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.chat.ChatApiDocument
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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v1/chats")
class ChatWebAdapter(

) : ChatApiDocument {
    @PostMapping
    override fun createChat(): ResponseEntity<ApiResult<CreateChatResponse>> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{chat_id}")
    override fun deleteChat(
        @PathVariable("chat_id") chatId: UUID
    ): ResponseEntity<ApiResult<Unit>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/{chat_id}/messages")
    override fun createMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid request: CreateMessageRequest
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/{chat_id}/voice")
    override fun createVoiceMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid request: CreateVoiceMessageRequest
    ): ResponseEntity<ApiResult<CreateVoiceMessageResponse>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/{chat_id}/images")
    override fun uploadImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid request: UploadImageInChatRequest
    ): ResponseEntity<ApiResult<UploadImageInChatResponse>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/{chat_id}/messages/generations")
    override fun generateImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody request: GenerateImageInChatRequest
    ): ResponseEntity<ApiResult<GenerateImageInChatResponse>> {
        TODO("Not yet implemented")
    }
}