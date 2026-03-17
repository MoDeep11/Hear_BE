package modeep.hear.infrastructure.adapter.`in`.auth

import modeep.hear.global.document.auth.AuthApiDocument
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ReissueRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.SendEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthWebAdapter() : AuthApiDocument {
    override fun login(request: LoginRequest): TokenResponse {
        TODO("Not yet implemented")
    }

    override fun register(request: RegisterRequest): TokenResponse {
        TODO("Not yet implemented")
    }

    override fun reissue(request: ReissueRequest): TokenResponse {
        TODO("Not yet implemented")
    }

    override fun sendVerificationEmail(request: SendEmailRequest) {
        TODO("Not yet implemented")
    }

    override fun verifyEmail(request: VerifyEmailRequest) {
        TODO("Not yet implemented")
    }

    override fun sendResetPasswordEmail(request: SendEmailRequest) {
        TODO("Not yet implemented")
    }

    override fun checkResetPasswordEmail(token: String) {
        TODO("Not yet implemented")
    }

    override fun resetPassword(request: ReissueRequest) {
        TODO("Not yet implemented")
    }

    override fun logout(request: LogoutRequest) {
        TODO("Not yet implemented")
    }
}
