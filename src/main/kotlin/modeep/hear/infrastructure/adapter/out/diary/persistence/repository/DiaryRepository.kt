package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryRepository : JpaRepository<DiaryJpaEntity, UUID>
