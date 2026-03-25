package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import jakarta.validation.constraints.Min
import java.util.UUID

data class UploadDiaryImageRequest(
    val imageUrl: String?,
    val id: UUID? = null,

    @field:Min(value = 0)
    val order: Int,
    val isDeleted: Boolean = false
)

//    {
//        // 이미지 추가
//        "images": "(File Binary)",
//        "id": null,  // image id
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
