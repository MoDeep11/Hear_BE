package modeep.hear.domain.storage.vo

import java.util.UUID

data class FileData(
    val fileName: String,
    val contentType: String,
    val size: Long,
    val type: ServiceType,
    val userId: UUID
)
