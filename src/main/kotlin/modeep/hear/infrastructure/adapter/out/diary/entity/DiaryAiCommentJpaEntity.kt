package modeep.hear.infrastructure.adapter.out.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import modeep.hear.global.common.entity.BaseTimeEntity
import java.util.UUID

@Entity
@Table(name = "diary_ai_comments")
class DiaryAiCommentJpaEntity(
    @Id
    @Column(name = "diary_id", nullable = false)
    val diaryId: UUID,

    @Column(name = "content", nullable = false, length = 1000)
    val content: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    val status: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING
) : BaseTimeEntity()
