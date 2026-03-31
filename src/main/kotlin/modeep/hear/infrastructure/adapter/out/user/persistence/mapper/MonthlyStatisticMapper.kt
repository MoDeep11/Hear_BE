package modeep.hear.infrastructure.adapter.out.user.persistence.mapper

import modeep.hear.domain.user.model.MonthlyStatistic
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.MonthlyStatisticJpaEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.MonthlyStatisticId
import org.springframework.stereotype.Component

@Component
class MonthlyStatisticMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(entity: MonthlyStatisticJpaEntity): MonthlyStatistic {
        return MonthlyStatistic(
            userId = entity.id.userId,
            targetYearMonth = entity.id.targetYearMonth,
            diaryCount = entity.diaryCount,
            photoCount = entity.photoCount,
            aiReportContent = entity.aiReportContent,
            emotionDistribution = entity.emotionDistribution,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: MonthlyStatistic): MonthlyStatisticJpaEntity {
        return MonthlyStatisticJpaEntity(
            id = MonthlyStatisticId(
                userId = model.userId,
                targetYearMonth = model.targetYearMonth
            ),
            diaryCount = model.diaryCount,
            photoCount = model.photoCount,
            writingRate = model.writingRate,
            aiReportContent = model.aiReportContent,
            emotionDistribution = model.emotionDistribution
        )
    }
}
