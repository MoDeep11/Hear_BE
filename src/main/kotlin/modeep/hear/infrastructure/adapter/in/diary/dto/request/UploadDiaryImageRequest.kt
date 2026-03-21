package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import jakarta.validation.constraints.Min
import modeep.hear.domain.s3.model.FileData
import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UploadDiaryImageRequest(
    val image: MultipartFile?,
    val id: UUID?,

    @field:Min(value = 0)
    val order: Int,
    val isDeleted: Boolean = false
) {
    fun toDomainFile(image: MultipartFile): FileData =
        FileData(
            fileName = image.originalFilename ?: "unknown",
            contentType = image.contentType ?: MediaType.APPLICATION_OCTET_STREAM_VALUE,
            content = image.inputStream,
            size = image.size
        )
}

//    {
//        // 이미지 추가
//        "images": "(File Binary)",
//        "id": null,
//        "order": 0,
//        "isDeleted": false
//    },
//    {
//        // 이미지 삭제
//        "images": null,
//        "id": "UUID",
//        "order": 1,
//        "isDeleted": true
//    },
//    {
//        // 이미지 순서 변경
//        "images": null,
//        "id": "UUID",
//        "order": 1,
//        "isDeleted": false
//    }
