package modeep.hear.infrastructure.external.openfeign.holiday

import modeep.hear.infrastructure.config.openfeign.HolidayFeignConfig
import modeep.hear.infrastructure.config.openfeign.OpenFeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "holiday-api",
    url = "\${api.holiday.url}",
    configuration = [
        OpenFeignConfig::class,
        HolidayFeignConfig::class
    ]
)
interface HolidayFeignClient {
    @GetMapping("/getRestDeInfo")
    fun getRestDays(
        @RequestParam("solYear") solYear: String,
        @RequestParam("solMonth") solMonth: String?,
        @RequestParam("_type") type: String = "json"
    ): HolidayResponse
}
