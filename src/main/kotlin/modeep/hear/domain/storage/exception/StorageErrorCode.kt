package modeep.hear.domain.storage.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class StorageErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    NOT_ALLOW_EXTENSION(HttpStatus.BAD_REQUEST, "STORAGE_001", "허용되지 않는 확장자입니다."),
    FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "STORAGE_002", "파일의 크기는 10MB 이하까지 허용됩니다."),

    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "STORAGE_003", "파일 삭제에 실패하였습니다"),
    INVALID_URL(HttpStatus.BAD_REQUEST, "STORAGE_004", "URL 형식이 올바르지 않습니다.")
}
