package modeep.hear.infrastructure.adapter.out.calendar.external

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import modeep.hear.domain.calendar.port.`in`.CalendarUseCase
import modeep.hear.domain.calendar.port.`in`.dto.SimpleHolidayInfo
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayFeignClient
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayItem
import modeep.hear.infrastructure.external.openfeign.holiday.HolidayResponse
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class CalendarExternalAdapter(
    private val holidayFeignClient: HolidayFeignClient,
    private val objectMapper: ObjectMapper
) : CalendarUseCase {

    override fun fetchHolidays(year: Int, month: Int): List<SimpleHolidayInfo> {
        val response = holidayFeignClient.getRestDays(year.toString(), String.format("%02d", month))

        val items = parseHolidayItems(response)

        return items.map {
            SimpleHolidayInfo(
                date = convertToLocalDate(it.locdate),
                name = it.dateName
            )
        }
    }

    private fun parseHolidayItems(response: HolidayResponse): List<HolidayItem> {
        val items = response.response.body?.items ?: return emptyList()
        if (items is String) return emptyList()

        val rootNode = objectMapper.valueToTree<JsonNode>(items)
        val itemNode = rootNode.get("item") ?: return emptyList()

        return if (itemNode.isArray) {
            objectMapper.convertValue(itemNode, object : TypeReference<List<HolidayItem>>() {})
        } else {
            listOf(objectMapper.convertValue(itemNode, HolidayItem::class.java))
        }
    }

    private fun convertToLocalDate(locdate: Int): LocalDate =
        LocalDate.parse(locdate.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"))
}