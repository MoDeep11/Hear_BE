package modeep.hear.global.document.user

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
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
import org.springframework.http.ResponseEntity
import java.time.YearMonth

@Tag(name = "User", description = "User 도메인 관련 API")
interface UserApiDocument {

    fun deleteUser(
        accessToken: String,
        request: DeleteUserRequest
    ): ResponseEntity<ApiResult<Unit>>

    fun getProfile(): ResponseEntity<ApiResult<UserProfileResponse>>

    fun getStatistics(
        yearMonth: YearMonth
    ): ResponseEntity<ApiResult<UserStatisticsResponse>>

    fun getSummary(): ResponseEntity<ApiResult<UserSummaryResponse>>

    fun updateNickname(
        request: UpdateNicknameRequest
    ): ResponseEntity<ApiResult<UpdateNicknameResponse>>

    fun updateProfileImage(
        request: UpdateProfileImageRequest
    ): ResponseEntity<ApiResult<UpdateProfileImageResponse>>

    fun updatePassword(
        request: UpdatePasswordRequest
    ): ResponseEntity<ApiResult<UpdatePasswordResponse>>

    fun updateEmailSubscription(
        request: UpdateEmailSubscriptionRequest
    ): ResponseEntity<ApiResult<UpdateEmailSubscriptionResponse>>
}
