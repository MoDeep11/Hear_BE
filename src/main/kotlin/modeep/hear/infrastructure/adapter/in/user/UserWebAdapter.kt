package modeep.hear.infrastructure.adapter.`in`.user

import jakarta.validation.Valid
import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.user.UserApiDocument
import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
