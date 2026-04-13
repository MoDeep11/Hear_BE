package modeep.hear.global.common.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import modeep.hear.global.common.annotation.validator.NotBlankIfPresentValidator
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotBlankIfPresentValidator::class])
annotation class NotBlankIfPresent(
    val message: String = "값이 있다면 공백일 수 없습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
