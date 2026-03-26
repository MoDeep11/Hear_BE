package modeep.hear.domain.storage.port.out

import modeep.hear.domain.storage.vo.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GenerateUploadUrlResponse

interface StoragePort {
    fun generateUploadUrl(file: FileData): GenerateUploadUrlResponse

    fun delete(s3Url: String)
}
