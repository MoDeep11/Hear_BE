package modeep.hear.global.common.annotation.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import modeep.hear.global.common.annotation.NotBlankIfPresent

class NotBlankIfPresentValidator : ConstraintValidator<NotBlankIfPresent, String?> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        return value.isNotBlank()
    }
}
