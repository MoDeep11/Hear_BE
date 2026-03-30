package modeep.hear.infrastructure.adapter.out.diary.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import modeep.hear.domain.diary.vo.DiaryAiCommentStatus
import modeep.hear.global.common.entity.BaseTimeEntity
import java.util.UUID

@Entity
@Table(name = "diary_ai_comments")
class DiaryAiCommentJpaEntity(
    @Id
    val diaryId: UUID,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    val diary: DiaryJpaEntity,

    @Column(name = "content", length = 1000)
    val content: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    val status: DiaryAiCommentStatus = DiaryAiCommentStatus.PENDING
) : BaseTimeEntity()
