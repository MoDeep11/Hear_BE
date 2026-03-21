package modeep.hear.domain.s3.port.out

import modeep.hear.domain.s3.model.FileData

interface S3Port {
    fun upload(file: FileData): String

    fun delete(s3Url: String)
}
