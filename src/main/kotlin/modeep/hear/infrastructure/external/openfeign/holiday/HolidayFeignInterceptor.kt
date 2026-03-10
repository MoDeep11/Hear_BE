package modeep.hear.infrastructure.external.openfeign.holiday

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class HolidayFeignInterceptor(
    @Value("\${api.holiday.service-key}") private val serviceKey: String,
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        template.query("ServiceKey", serviceKey)
    }
}
