package modeep.hear.domain.s3.model

import java.io.InputStream

data class FileData(
    val fileName: String,
    val contentType: String,
    val content: InputStream,
    val size: Long
)
