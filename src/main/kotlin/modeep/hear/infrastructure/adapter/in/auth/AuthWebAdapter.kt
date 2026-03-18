package modeep.hear.infrastructure.adapter.`in`.auth

import jakarta.validation.Valid
import modeep.hear.domain.auth.port.`in`.LoginAuthUseCase
import modeep.hear.domain.auth.port.`in`.LogoutAuthUseCase
import modeep.hear.domain.auth.port.`in`.RegisterAuthUseCase
import modeep.hear.domain.auth.port.`in`.ReissueAuthUseCase
import modeep.hear.domain.auth.port.`in`.ResetPasswordAuthUseCase
import modeep.hear.domain.auth.port.`in`.SendEmailAuthUseCase
import modeep.hear.domain.auth.port.`in`.VerifyEmailAuthUseCase
import modeep.hear.domain.auth.port.`in`.VerifyResetTicketAuthUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.auth.AuthApiDocument
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ResetPasswordRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthWebAdapter(
    private val loginAuthUseCase: LoginAuthUseCase,
    private val registerAuthUseCase: RegisterAuthUseCase,
    private val sendEmailAuthUseCase: SendEmailAuthUseCase,
    private val verifyEmailAuthUseCase: VerifyEmailAuthUseCase,
    private val resetPasswordAuthUseCase: ResetPasswordAuthUseCase,
    private val logoutAuthUseCase: LogoutAuthUseCase,
    private val reissueAuthUseCase: ReissueAuthUseCase,
    private val verifyResetTicketAuthUseCase: VerifyResetTicketAuthUseCase
) : AuthApiDocument {

    @PostMapping("/login")
    override fun login(
        @RequestBody @Valid
        request: LoginRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = loginAuthUseCase.execute(request)
            )
        )
    }

    @PostMapping("/register")
    override fun register(
        @RequestBody @Valid
        request: RegisterRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = registerAuthUseCase.execute(request)
            )
        )
    }

    @PostMapping("/reissue")
    override fun reissue(
        @RequestHeader("Authorization") accessToken: String,
        @RequestBody @Valid
        request: ReissueRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = reissueAuthUseCase.execute(request, accessToken)
            )
        )
    }

    @PostMapping("/email")
    override fun sendEmail(
        @RequestBody @Valid
        request: SendEmailRequest
    ): ResponseEntity<ApiResult<Unit>> {
        sendEmailAuthUseCase.execute(request)
        return ResponseEntity.ok(ApiResult())
    }

    @PostMapping("/email-tickets")
    override fun verifyEmail(
        @RequestBody @Valid
        request: VerifyEmailRequest
    ): ResponseEntity<ApiResult<String>> {
        return ResponseEntity.ok(
            ApiResult(
                data = verifyEmailAuthUseCase.execute(request)
            )
        )
    }

    @GetMapping("/password-resets")
    override fun verifyResetTicket(
        @RequestParam ticket: String
    ): ResponseEntity<ApiResult<Unit>> {
        verifyResetTicketAuthUseCase.execute(ticket)
        return ResponseEntity.ok(ApiResult())
    }

    @PatchMapping("/password-resets")
    override fun resetPassword(
        @RequestBody @Valid
        request: ResetPasswordRequest
    ): ResponseEntity<ApiResult<Unit>> {
        resetPasswordAuthUseCase.execute(request)
        return ResponseEntity.ok(ApiResult())
    }

    @PostMapping("/logout")
    override fun logout(
        @RequestHeader("Authorization") accessToken: String,
        @RequestBody @Valid
        request: LogoutRequest
    ): ResponseEntity<ApiResult<Unit>> {
        logoutAuthUseCase.execute(request, accessToken)
        return ResponseEntity.ok(ApiResult())
    }
}
