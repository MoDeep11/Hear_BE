package modeep.hear.infrastructure.adapter.`in`.auth

import jakarta.validation.Valid
import modeep.hear.domain.auth.port.`in`.LoginUseCase
import modeep.hear.domain.auth.port.`in`.RegisterAuthUseCase
import modeep.hear.domain.auth.port.`in`.SendEmailUseCase
import modeep.hear.domain.auth.port.`in`.VerifyEmailAuthUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.auth.AuthApiDocument
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthWebAdapter(
    private val loginUseCase: LoginUseCase,
    private val registerAuthUseCase: RegisterAuthUseCase,
    private val sendEmailUseCase: SendEmailUseCase,
    private val verifyEmailAuthUseCase: VerifyEmailAuthUseCase
) : AuthApiDocument {

    @PostMapping("/login")
    override fun login(
        @RequestBody @Valid request: LoginRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = loginUseCase.execute(request)
            )
        )
    }

    @PostMapping("/register")
    override fun register(
        @RequestBody @Valid request: RegisterRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = registerAuthUseCase.execute(request)
            )
        )
    }

    @PostMapping("/reissue")
    override fun reissue(
        @RequestBody @Valid request: ReissueRequest
    ): ResponseEntity<ApiResult<TokenResponse>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/email-verifications")
    override fun sendVerificationEmail(
        @RequestBody @Valid request: SendEmailRequest
    ): ResponseEntity<ApiResult<Unit>> {
        sendEmailUseCase.execute(request)
        return ResponseEntity.ok(ApiResult())
    }

    @PostMapping("/email-tickets")
    override fun verifyEmail(
        @RequestBody @Valid request: VerifyEmailRequest
    ): ResponseEntity<ApiResult<String>> {
        return ResponseEntity.ok(ApiResult(
            data = verifyEmailAuthUseCase.execute(request)
        ))
    }

    @PostMapping("/password-resets")
    override fun sendResetPasswordEmail(
        @RequestBody @Valid request: SendEmailRequest
    ): ResponseEntity<ApiResult<Unit>> {
        TODO("Not yet implemented")
    }

    @GetMapping("/password-resets")
    override fun checkResetPasswordEmail(
        @RequestParam token: String
    ): ResponseEntity<ApiResult<Unit>> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/password-resets")
    override fun resetPassword(
        @RequestBody @Valid request: ReissueRequest
    ): ResponseEntity<ApiResult<Unit>> {
        TODO("Not yet implemented")
    }

    @PostMapping("/logout")
    override fun logout(
        @RequestBody @Valid request: LogoutRequest
    ): ResponseEntity<ApiResult<Unit>> {
        TODO("Not yet implemented")
    }
}
