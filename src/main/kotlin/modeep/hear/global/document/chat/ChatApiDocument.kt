package modeep.hear.global.document.chat

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import org.springframework.http.ResponseEntity
import java.util.UUID

@Tag(name = "Chat", description = "Chat 도메인 관련 API")
interface ChatApiDocument {

    fun createChat(): ResponseEntity<ApiResult<Unit>>

    fun createMessage(
        chatId: UUID,
    ): ResponseEntity<ApiResult<Unit>>

    fun createVoiceMessage(
        chatId: UUID,
    ): ResponseEntity<ApiResult<Unit>>

    fun uploadImage(
        chatId: UUID,
    ): ResponseEntity<ApiResult<Unit>>

    fun generateImage(
        chatId: UUID,
    ): ResponseEntity<ApiResult<Unit>>

    fun deleteChat(
        chatId: UUID
    ): ResponseEntity<ApiResult<Unit>>
}
