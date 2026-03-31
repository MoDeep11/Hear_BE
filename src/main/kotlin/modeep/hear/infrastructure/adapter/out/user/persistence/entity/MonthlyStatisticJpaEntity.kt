package modeep.hear.infrastructure.adapter.out.user.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import modeep.hear.domain.common.vo.EmotionDistribution
import modeep.hear.global.common.entity.BaseTimeEntity
import modeep.hear.infrastructure.adapter.out.user.persistence.entity.id.MonthlyStatisticId
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "monthly_statistics")
class MonthlyStatisticJpaEntity(

    @EmbeddedId
    val id: MonthlyStatisticId,

    @Column(name = "diary_count", nullable = false)
    var diaryCount: Int = 0,

    @Column(name = "photo_count", nullable = false)
    var photoCount: Int = 0,

    @Column(name = "writing_rate", nullable = false)
    var writingRate: Double = 0.0,

    @Column(name = "ai_report_content", length = 1000)
    var aiReportContent: String? = null,

    @JdbcTypeCode(SqlTypes.JSON) // jsonb 타입
    @Column(name = "emotion_distribution", columnDefinition = "jsonb", nullable = false)
    var emotionDistribution: EmotionDistribution

) : BaseTimeEntity()
