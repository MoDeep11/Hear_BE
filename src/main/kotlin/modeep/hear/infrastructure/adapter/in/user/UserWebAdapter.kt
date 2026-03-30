package modeep.hear.infrastructure.adapter.`in`.user

import jakarta.validation.Valid
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.user.UserApiDocument
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateEmailSubscriptionRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateNicknameRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdatePasswordRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileImageRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateEmailSubscriptionResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateNicknameResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdatePasswordResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileImageResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserProfileResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserStatisticsResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserSummaryResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/user/me")
class UserWebAdapter(
    private val deleteUserUseCase: DeleteUserUseCase
) : UserApiDocument {

    @DeleteMapping
    override fun deleteUser(
        @RequestHeader("Authorization") accessToken: String,
        @RequestBody @Valid
        request: DeleteUserRequest
    ): ResponseEntity<ApiResult<Unit>> {
        deleteUserUseCase.execute(
            accessToken = accessToken,
            request = request
        )
        return ResponseEntity.ok(ApiResult())
    }

    override fun getProfile(): ResponseEntity<ApiResult<UserProfileResponse>> {
        TODO("Not yet implemented")
    }

    override fun getStatistics(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM")
        yearMonth: YearMonth
    ): ResponseEntity<ApiResult<UserStatisticsResponse>> {
        TODO("Not yet implemented")
    }

    override fun getSummary(): ResponseEntity<ApiResult<UserSummaryResponse>> {
        TODO("Not yet implemented")
    }

    override fun updateNickname(
        @RequestBody @Valid request: UpdateNicknameRequest
    ): ResponseEntity<ApiResult<UpdateNicknameResponse>> {
        TODO("Not yet implemented")
    }

    override fun updateProfileImage(
        @RequestBody @Valid request: UpdateProfileImageRequest
    ): ResponseEntity<ApiResult<UpdateProfileImageResponse>> {
        TODO("Not yet implemented")
    }

    override fun updatePassword(
        @RequestBody @Valid request: UpdatePasswordRequest
    ): ResponseEntity<ApiResult<UpdatePasswordResponse>> {
        TODO("Not yet implemented")
    }

    override fun updateEmailSubscription(
        @RequestBody @Valid request: UpdateEmailSubscriptionRequest
    ): ResponseEntity<ApiResult<UpdateEmailSubscriptionResponse>> {
        TODO("Not yet implemented")
    }
}
