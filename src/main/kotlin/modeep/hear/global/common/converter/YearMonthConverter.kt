package modeep.hear.global.common.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Converter(autoApply = true) // 프로젝트 전체에 적용
class YearMonthConverter : AttributeConverter<YearMonth, String> {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

    // 객체 -> String
    override fun convertToDatabaseColumn(attribute: YearMonth?): String? {
        return attribute?.format(formatter)
    }

    // String -> 객체
    override fun convertToEntityAttribute(dbData: String?): YearMonth? {
        return dbData?.let {
            YearMonth.parse(it, formatter)
        }
    }
}
