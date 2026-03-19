package modeep.hear.infrastructure.adapter.out.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.global.common.entity.BaseUUIDEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.YearMonth
import java.util.UUID

@Entity
@Table(name = "monthly_statistics")
class MonthlyStatisticJpaEntity(

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "target_year_month", nullable = false)
    val targetYearMonth: YearMonth,

    @Column(name = "diary_count", nullable = false)
    var diaryCount: Int = 0,

    @Column(name = "photo_count", nullable = false)
    var photoCount: Int = 0,

    @Column(name = "writing_rate", nullable = false)
    var writingRate: Float = 0.0f,

    @Column(name = "ai_report_content", length = 1000)
    var aiReportContent: String? = null,

    @JdbcTypeCode(SqlTypes.JSON) // jsonb 타입
    @Column(name = "emotion_distribution", columnDefinition = "jsonb", nullable = false)
    var emotionDistribution: EmotionDistribution,

    id: UUID? = null
) : BaseUUIDEntity(id)
