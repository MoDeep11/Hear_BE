package modeep.hear.global.error

import org.springframework.http.HttpStatus

interface ErrorCode {
    val name: String
    val status: HttpStatus
    val code: String
    val message: String
}