package modeep.hear.global.document.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Auth", description = "Auth 도메인 관련 API")
interface AuthApiDocument {

    @Operation(
        summary = "로그인",
        description = "유저 정보를 기반으로 로그인합니다."
    )
    @ApiInternalServerErrorResponse
    fun login(
        @RequestBody @Valid
        request: LoginRequest
    ): TokenResponse

    @Operation(
        summary = "회원가입",
        description = "새로운 유저를 생성합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "유저 회원가입 성공",
        content = [
            Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = TokenResponse::class)
            )
        ]
    )
    @ApiInternalServerErrorResponse
    fun register(
        @RequestBody @Valid
        request: RegisterRequest
    ): TokenResponse

    @ApiInternalServerErrorResponse
    fun reissue(
        @RequestBody @Valid
        request: ReissueRequest
    ): TokenResponse

    @ApiInternalServerErrorResponse
    fun sendVerificationEmail(
        @RequestBody @Valid
        request: SendEmailRequest
    )

    @ApiInternalServerErrorResponse
    fun verifyEmail(
        @RequestBody @Valid
        request: VerifyEmailRequest
    )

    @ApiInternalServerErrorResponse
    fun sendResetPasswordEmail(
        @RequestBody @Valid
        request: SendEmailRequest
    )

    @ApiInternalServerErrorResponse
    fun checkResetPasswordEmail(
        @RequestParam token: String
    )

    @ApiInternalServerErrorResponse
    fun resetPassword(
        @RequestBody @Valid
        request: ReissueRequest
    )

    @ApiInternalServerErrorResponse
    fun logout(
        @RequestBody @Valid
        request: LogoutRequest
    )
}
