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

    CANNOT_ACCESS_DIARY(HttpStatus.FORBIDDEN, "DIARY_003", "일기의 작성자만 접근할 수 있는 행동입니다."),

    DIARY_IMAGE_GENERATION_FAILED(HttpStatus.BAD_GATEWAY, "DIARY_005", "AI 이미지를 생성하는 데 실패했습니다."),

    TOO_MANY_IMAGES(HttpStatus.BAD_REQUEST, "DIARY_006", "사진은 최대 10개까지 올릴 수 있습니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "DIARY_007", "기존 사진을 찾을 수 없습니다.")
}
