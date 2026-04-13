package modeep.hear.infrastructure.adapter.`in`.chat

import jakarta.validation.Valid
import kotlinx.coroutines.runBlocking
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
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatMetaRequest
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateAiImageTaskResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateChatResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.response.CreateMessageResponse
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.UploadDiaryImageResponse
import modeep.hear.infrastructure.security.userdetails.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
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
    override fun createChat(
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateChatResponse>> {
        val user = (authentication.principal as CustomUserDetails).getUser()
        val result = runBlocking {
            createChatUseCase.execute(user)
        }
        return ResponseEntity.ok(ApiResult(data = result))
    }

    @PatchMapping("/{chat_id}")
    override fun finishChat(
        @PathVariable("chat_id") chatId: UUID,
        authentication: Authentication
    ): ResponseEntity<ApiResult<Unit>> {
        val user = (authentication.principal as CustomUserDetails).getUser()
        runBlocking {
            finishChatUseCase.execute(chatId, user)
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(
                ApiResult(
                    status = 202,
                    message = AiImageTaskStatus.RESERVED.name
                )
            )
    }

    @PostMapping("/{chat_id}/messages")
    override fun createMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateMessageRequest,
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        val user = (authentication.principal as CustomUserDetails).getUser()
        val result = runBlocking {
            createMessageUseCase.executeText(chatId, request, user)
        }
        return ResponseEntity.ok(ApiResult(data = result))
    }

    @PostMapping("/{chat_id}/voice", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun createVoiceMessage(
        @PathVariable("chat_id") chatId: UUID,
        @RequestPart("voice") voice: MultipartFile,
        authentication: Authentication
    ): ResponseEntity<ApiResult<CreateMessageResponse>> {
        val user = (authentication.principal as CustomUserDetails).getUser()
        val result = runBlocking {
            createMessageUseCase.executeVoice(chatId, voice, user)
        }
        return ResponseEntity.ok(ApiResult(data = result))
    }

    @PostMapping("/{chat_id}/images", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun uploadImageInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestPart("files") files: List<MultipartFile>,
        @RequestPart("requests") @Valid requests: List<UploadImageInChatMetaRequest>
    ): ResponseEntity<ApiResult<List<UploadDiaryImageResponse>>> {
        val result = uploadImageInChatUseCase.execute(chatId, files, requests)
        return ResponseEntity.ok(ApiResult(data = result))
    }

    @PostMapping("/{chat_id}/images/generations")
    override fun createAiImageTaskInChat(
        @PathVariable("chat_id") chatId: UUID,
        @RequestBody @Valid
        request: CreateAiImageTaskRequest
    ): ResponseEntity<ApiResult<CreateAiImageTaskResponse>> {
        val res = createAiImageTaskInChatUseCase.execute(chatId, request)
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(
                ApiResult(
                    data = CreateAiImageTaskResponse.from(res),
                    status = 202,
                    message = res.status.name
                )
            )
    }
}
