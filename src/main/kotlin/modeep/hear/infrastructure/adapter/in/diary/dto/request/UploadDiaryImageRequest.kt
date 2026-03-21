package modeep.hear.infrastructure.adapter.`in`.diary.dto.request

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UploadDiaryImageRequest(
    val image: MultipartFile?,
    val id: UUID?,  // 새로운 이미지 일 경우 null
    val order: Int,
    val isDeleted: Boolean = false
)

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
