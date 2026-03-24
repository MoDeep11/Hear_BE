package modeep.hear.domain.s3.port.out

import modeep.hear.domain.s3.model.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse

interface S3Port {
    fun getPreSignedUrl(file: FileData): GetPresignedUrlResponse

    fun delete(s3Url: String)
}
