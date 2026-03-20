package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UploadDiaryImageRequest(
    val images: MultipartFile,
    val id: UUID?,  // 새로운 이미지 일 경우 null
    val order: Int,
    val isDeleted: Boolean = false
)