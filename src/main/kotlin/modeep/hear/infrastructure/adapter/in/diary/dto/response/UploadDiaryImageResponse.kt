package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.vo.DiarySourceType
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

data class UploadDiaryImageResponse(
    val id: UUID,
    val url: String,
    val order: Int,
    val type: DiarySourceType = DiarySourceType.MANUAL
) {
    companion object {
        fun toResponse(
            id: UUID,
            url: String? = null,
            order: Int,
            type: DiarySourceType
        ) = UploadDiaryImageResponse(
            id = id,
            url = url ?: throw BusinessException(DiaryErrorCode.INVALID_VALUE, "이미지는 비어있을 수 없습니다."),
            order = order,
            type = type
        )
    }
}
