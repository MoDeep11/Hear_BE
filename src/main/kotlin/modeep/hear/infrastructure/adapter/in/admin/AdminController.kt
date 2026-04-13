package modeep.hear.infrastructure.adapter.`in`.admin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.out.DiaryPort
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.domain.storage.port.`in`.UploadImageUseCase
import modeep.hear.global.common.annotation.NotBlankIfPresent
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.chat.dto.request.UploadImageInChatMetaRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Admin", description = "Admin 관리용 API")
@RequestMapping("/api/v1/admin")
@RestController
class AdminController(
    private val adminCreateDiaryService: AdminCreateDiaryService,
    private val uploadImageUseCase: UploadImageUseCase
) {
    @Operation(
        summary = "일기 생성",
        description = "일기 데이터를 추가합니다"
    )
    @ApiResponse(
        responseCode = "201",
        description = "일기 생성 성공"
    )
    @RequestBody(
        description = "일기 생성 요청",
        required = true,
        content = [
            Content(
                mediaType = "application/json"
            )
        ]
    )
    @ApiInternalServerErrorResponse
    @PostMapping("/diaries")
    fun createDiary(
        @RequestParam("data") @Valid
        request: AdminCreateDiaryRequest,
        @RequestParam("metadata", required = false)
        metadata: List<UploadImageInChatMetaRequest>?,
        @RequestParam("images", required = false) images: List<MultipartFile>?
    ): ResponseEntity<ApiResult<Unit>> {
        val diaryImages = if (metadata != null && metadata.size != images!!.size) {
            uploadImageUseCase.executeInChat(
                images = images,
                requests = metadata
            )
        } else {
            emptyList()
        }
        adminCreateDiaryService.execute(request, diaryImages)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResult(
                    status = 201,
                    message = "Diary created"
                )
            )
    }
}

data class AdminCreateDiaryRequest(
    @field:NotBlank
    val content: String,
    val emotion: Emotion,
    @field:NotEmpty
    val tags: List<@NotBlank String>,
    @field:NotBlankIfPresent
    val aiComment: String? = null
)

@Service
class AdminCreateDiaryService(
    private val diaryPort: DiaryPort,
    private val securityPort: SecurityPort
) {
    fun execute(req: AdminCreateDiaryRequest, images: List<DiaryImage>) {
        val userId = securityPort.getCurrentUserId()
        val diary = Diary.create(
            userId = userId,
            content = req.content,
            emotion = req.emotion,
            tags = req.tags,
            sourceType = DiarySourceType.MANUAL
        )
        diary.updateImages(images)
        diaryPort.save(diary)
    }
}
