package modeep.hear.infrastructure.adapter.`in`.diary.dto.response

import modeep.hear.domain.diary.vo.DiarySourceType
import java.util.UUID

data class UploadDiaryImageResponse(
    val id : UUID,
    val url : String,
    val type : DiarySourceType = DiarySourceType.MANUAL,
)