package modeep.hear.domain.s3.port.out

import modeep.hear.domain.s3.model.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GeneratePresignedUrlResponse

interface S3Port {
    fun generatePreSignedUrl(file: FileData): GeneratePresignedUrlResponse

    fun delete(s3Url: String)
}
