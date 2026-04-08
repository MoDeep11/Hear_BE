package modeep.hear.infrastructure.adapter.`in`.user

import jakarta.validation.Valid
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.domain.user.port.`in`.GetRandomProfileImageUseCase
import modeep.hear.domain.user.port.`in`.GetUserCalendarUseCase
import modeep.hear.domain.user.port.`in`.GetUserProfileUseCase
import modeep.hear.domain.user.port.`in`.GetUserStatisticsUseCase
import modeep.hear.domain.user.port.`in`.GetUserSummaryUseCase
import modeep.hear.domain.user.port.`in`.UpdateEmailSubscriptionUseCase
import modeep.hear.domain.user.port.`in`.UpdatePasswordUseCase
import modeep.hear.domain.user.port.`in`.UpdateProfileUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.user.UserApiDocument
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateEmailSubscriptionRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdatePasswordRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetRandomProfileImageResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetUserCalendarResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateEmailSubscriptionResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdatePasswordResponse
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse
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
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/users/me")
class UserWebAdapter(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserStatisticsUseCase: GetUserStatisticsUseCase,
    private val getUserSummaryUseCase: GetUserSummaryUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val updateEmailSubscriptionUseCase: UpdateEmailSubscriptionUseCase,
    private val getUserCalendarUseCase: GetUserCalendarUseCase,
    private val getRandomProfileImageUseCase: GetRandomProfileImageUseCase
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
        return ResponseEntity.ok(
            ApiResult(
                message = "Successfully withdrew user"
            )
        )
    }

    @GetMapping
    override fun getProfile(): ResponseEntity<ApiResult<UserProfileResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                message = "Get user profile",
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
                message = "Get user statistics",
                data = getUserStatisticsUseCase.execute(effectiveYearMonth)
            )
        )
    }

    @GetMapping("/summary")
    override fun getSummary(): ResponseEntity<ApiResult<UserSummaryResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                message = "Get user summary",
                data = getUserSummaryUseCase.execute()
            )
        )
    }

    @PatchMapping("/profile")
    override fun updateProfile(
        @RequestPart("data") @Valid
        request: UpdateProfileRequest,
        @RequestParam("image") image: MultipartFile
    ): ResponseEntity<ApiResult<UpdateProfileResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                message = "Update profile",
                data = updateProfileUseCase.execute(request, image)
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
                message = "Update password",
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
                message = "Update email subscription",
                data = updateEmailSubscriptionUseCase.execute(request)
            )
        )
    }

    @GetMapping("/calendars")
    override fun getUserCalendar(
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM")
        yearMonth: YearMonth?
    ): ResponseEntity<ApiResult<List<GetUserCalendarResponse>>> {
        val effectiveYearMonth = yearMonth ?: YearMonth.now()
        val res = getUserCalendarUseCase.execute(effectiveYearMonth)
        return ResponseEntity.ok(
            ApiResult(
                message = "Get user calendar",
                data = res
            )
        )
    }

    @GetMapping("/profile-images/random")
    override fun getRandomProfileImage(): ResponseEntity<ApiResult<GetRandomProfileImageResponse>> {
        return ResponseEntity.ok(
            ApiResult(
                message = "Get random profile image",
                data = getRandomProfileImageUseCase.execute()
            )
        )
    }
}
