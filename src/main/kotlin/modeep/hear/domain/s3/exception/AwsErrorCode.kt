package modeep.hear.domain.s3.exception

import modeep.hear.global.error.ErrorCode
import org.springframework.http.HttpStatus

enum class AwsErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ErrorCode {
    NOT_ALLOW_EXTENSION(HttpStatus.BAD_REQUEST, "AWS_001", "허용되지 않는 확장자입니다."),
    FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "AWS_002", "파일의 크기는 10MB 이하까지 허용됩니다."),
}