package modeep.hear.domain.diary.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class DiaryErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "DIARY_001", "일기를 찾을 수 없습니다."),
    DIARY_IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "DIARY_002", "해당 일기에 사진이 없습니다.")
}
