package modeep.hear.infrastructure.adapter.out.diary.persistence.repository

import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryImageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface DiaryImageRepository : JpaRepository<DiaryImageJpaEntity, UUID> {
    fun findAllByChatId(chatId: UUID): List<DiaryImageJpaEntity>

    @Modifying
    @Query("DELETE FROM DiaryImageJpaEntity d WHERE d.id = :id")
    fun deleteIfExists(id: UUID)
}
