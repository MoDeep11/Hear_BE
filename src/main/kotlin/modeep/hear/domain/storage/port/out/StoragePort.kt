package modeep.hear.domain.storage.port.out

import modeep.hear.domain.storage.vo.FileData
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse

interface StoragePort {
    fun generateUploadUrl(file: FileData): GenerateUploadUrlResponse

    fun deleteAll(urls: List<String>)
}
