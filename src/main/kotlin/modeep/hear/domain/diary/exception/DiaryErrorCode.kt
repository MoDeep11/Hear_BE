package modeep.hear.domain.diary.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class DiaryErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "DIARY_001", "일기를 찾을 수 없습니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "DIARY_002", "유효하지 않은 값입니다."),

    CANNOT_UPDATE_DIARY(HttpStatus.FORBIDDEN, "DIARY_003", "일기를 수정할 수 없습니다."),
    CANNOT_DELETE_DIARY(HttpStatus.FORBIDDEN, "DIARY_004", "일기를 삭제할 수 없습니다."),

    IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "DIARY_005", "사진이 없습니다."),
}
