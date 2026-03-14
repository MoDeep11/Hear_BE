package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.global.common.entity.BaseEntity
import modeep.hear.global.converter.EmotionDistributionConverter
import java.time.YearMonth
import java.util.UUID

@Entity
@Table(name = "monthly_statistics")
class MonthlyStatisticJpaEntity(

    @Column(name = "user_id")
    val userId: UUID? = null,

    @Column(name = "target_year_month", nullable = false)
    val targetYearMonth: YearMonth,

    @Column(name = "diary_count", nullable = false)
    var diaryCount: Int = 0,

    @Column(name = "photo_count", nullable = false)
    var photoCount: Int = 0,

    @Column(name = "writing_rate", nullable = false)
    var writingRate: Float = 0.0f,

    @Column(name = "ai_report_content", columnDefinition = "TEXT")
    var aiReportContent: String? = null,

    @Convert(converter = EmotionDistributionConverter::class)
    @Column(name = "emotion_distribution", columnDefinition = "TEXT", nullable = false)
    var emotionDistribution: EmotionDistribution
) : BaseEntity()
