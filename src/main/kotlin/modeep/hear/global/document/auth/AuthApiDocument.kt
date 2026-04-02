package modeep.hear.global.document.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ResetPasswordRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.VerifyEmailResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@SecurityRequirements
@Tag(name = "Auth", description = "Auth 도메인 관련 API")
interface AuthApiDocument {

    @Operation(
        summary = "로그인",
        description = "유저 정보를 기반으로 로그인합니다."
    )
    @ApiInternalServerErrorResponse
    fun login(
        request: LoginRequest
    ): ResponseEntity<ApiResult<TokenResponse>>

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
        request: RegisterRequest
    ): ResponseEntity<ApiResult<TokenResponse>>

    @ApiInternalServerErrorResponse
    fun reissue(
        accessToken: String,
        request: ReissueRequest
    ): ResponseEntity<ApiResult<TokenResponse>>

    @ApiInternalServerErrorResponse
    fun sendEmail(
        request: SendEmailRequest
    ): ResponseEntity<ApiResult<Unit>>

    @ApiInternalServerErrorResponse
    fun verifyEmail(
        request: VerifyEmailRequest
    ): ResponseEntity<ApiResult<VerifyEmailResponse>>

    @ApiInternalServerErrorResponse
    fun verifyResetTicket(
        ticket: String
    ): ResponseEntity<ApiResult<Unit>>

    @ApiInternalServerErrorResponse
    fun resetPassword(
        request: ResetPasswordRequest
    ): ResponseEntity<ApiResult<Unit>>

    @SecurityRequirement(name = "BearerAuth")
    @ApiInternalServerErrorResponse
    fun logout(
        accessToken: String,
        request: LogoutRequest
    ): ResponseEntity<ApiResult<Unit>>
}
