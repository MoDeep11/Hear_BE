package modeep.hear.domain.storage.port.out

import modeep.hear.domain.storage.vo.FileData
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse
import org.springframework.web.multipart.MultipartFile

interface StoragePort {
    fun generateUploadUrl(file: FileData): GenerateUploadUrlResponse

    fun getUrlToUpload(fileData: FileData): String

    fun upload(file: MultipartFile, fileData: FileData): String

    fun deleteAll(urls: List<String>)

    fun deleteAllByKeys(keys: List<String>)

    fun extractKey(url: String): String
}
