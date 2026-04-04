package modeep.hear.infrastructure.adapter.`in`.user

import jakarta.validation.Valid
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.domain.user.port.`in`.GetUserCalendarUseCase
import modeep.hear.domain.user.port.`in`.GetUserProfileUseCase
import modeep.hear.domain.user.port.`in`.GetUserStatisticsUseCase
import modeep.hear.domain.user.port.`in`.GetUserSummaryUseCase
import modeep.hear.domain.user.port.`in`.UpdateEmailSubscriptionUseCase
import modeep.hear.domain.user.port.`in`.UpdateNicknameUserUseCase
import modeep.hear.domain.user.port.`in`.UpdatePasswordUseCase
import modeep.hear.domain.user.port.`in`.UpdateProfileImageUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.user.UserApiDocument
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateEmailSubscriptionRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateNicknameRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdatePasswordRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileImageRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetUserCalendarResponse
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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/user/me")
class UserWebAdapter(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserStatisticsUseCase: GetUserStatisticsUseCase,
    private val getUserSummaryUseCase: GetUserSummaryUseCase,
    private val updateNicknameUserUseCase: UpdateNicknameUserUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val updateEmailSubscriptionUseCase: UpdateEmailSubscriptionUseCase,
    private val getUserCalendarUseCase: GetUserCalendarUseCase
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

    @GetMapping
    override fun getProfile(): ResponseEntity<ApiResult<UserProfileResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = getUserProfileUseCase.execute()
            )
        )
    }

    @GetMapping("/statistics")
    override fun getStatistics(
        @RequestParam
        @DateTimeFormat(pattern = "yyyy-MM")
        yearMonth: YearMonth?
    ): ResponseEntity<ApiResult<UserStatisticsResponse>> {
        val effectiveYearMonth = yearMonth ?: YearMonth.now()
        return ResponseEntity.ok(
            ApiResult(
                data = getUserStatisticsUseCase.execute(effectiveYearMonth)
            )
        )
    }

    @GetMapping("/summary")
    override fun getSummary(): ResponseEntity<ApiResult<UserSummaryResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = getUserSummaryUseCase.execute()
            )
        )
    }

    @PatchMapping("/nickname")
    override fun updateNickname(
        @RequestBody @Valid
        request: UpdateNicknameRequest
    ): ResponseEntity<ApiResult<UpdateNicknameResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = updateNicknameUserUseCase.execute(request)
            )
        )
    }

    @PatchMapping("/profile-image")
    override fun updateProfileImage(
        @RequestBody @Valid
        request: UpdateProfileImageRequest
    ): ResponseEntity<ApiResult<UpdateProfileImageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = updateProfileImageUseCase.execute(request)
            )
        )
    }

    @PatchMapping("/password")
    override fun updatePassword(
        @RequestBody @Valid
        request: UpdatePasswordRequest
    ): ResponseEntity<ApiResult<UpdatePasswordResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = updatePasswordUseCase.execute(request)
            )
        )
    }

    @PatchMapping("/email-subscription")
    override fun updateEmailSubscription(
        @RequestBody @Valid
        request: UpdateEmailSubscriptionRequest
    ): ResponseEntity<ApiResult<UpdateEmailSubscriptionResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                data = updateEmailSubscriptionUseCase.execute(request)
            )
        )
    }

    override fun getUserCalendar(
        @RequestParam
        @DateTimeFormat(pattern = "yyyy-MM")
        yearMonth: YearMonth?
    ): ResponseEntity<ApiResult<List<GetUserCalendarResponse>>> {
        val effectiveYearMonth = yearMonth ?: YearMonth.now()
        val res = getUserCalendarUseCase.execute(effectiveYearMonth)
        return ResponseEntity.ok(ApiResult(data = res))
    }
}
