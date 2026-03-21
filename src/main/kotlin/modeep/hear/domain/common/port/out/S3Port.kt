package modeep.hear.domain.common.port.out

import org.springframework.web.multipart.MultipartFile

interface S3Port {
    fun upload(file: MultipartFile): String

    fun delete(s3Url: String)
}
