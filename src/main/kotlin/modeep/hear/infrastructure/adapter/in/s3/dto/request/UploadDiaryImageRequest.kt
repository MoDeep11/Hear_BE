package modeep.hear.infrastructure.adapter.`in`.s3.dto.request

import jakarta.validation.constraints.Min
import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.global.error.exception.BusinessException
import java.util.UUID

data class UploadDiaryImageRequest(
    val imageUrl: String?,
    val id: UUID? = null,

    @field:Min(value = 0)
    val order: Int,
    val isDeleted: Boolean = false
) {
    init {
        when {
            // 삭제 요청인데 ID가 없는 경우
            isDeleted && id == null ->
                throw BusinessException(DiaryErrorCode.INVALID_VALUE, "삭제 시 이미지 ID가 필요합니다.")

            // 추가 요청인데 URL이 없는 경우
            !isDeleted && id == null && imageUrl.isNullOrBlank() ->
                throw BusinessException(DiaryErrorCode.INVALID_VALUE, "새 이미지 추가 시 URL이 필요합니다.")

            // 수정 요청인데 ID가 없는 경우
            !isDeleted && id != null && !imageUrl.isNullOrBlank() ->
                throw BusinessException(DiaryErrorCode.INVALID_VALUE, "이미지 순서 변경 시에는 URL을 보낼 수 없습니다.")
        }
    }
}
