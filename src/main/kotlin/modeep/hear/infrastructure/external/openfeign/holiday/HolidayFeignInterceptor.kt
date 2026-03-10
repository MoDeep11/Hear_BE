package modeep.hear.infrastructure.external.openfeign.holiday

import feign.RequestInterceptor
import feign.RequestTemplate

class HolidayFeignInterceptor(
    private val serviceKey: String,
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        template.query("ServiceKey", serviceKey)
    }
}
