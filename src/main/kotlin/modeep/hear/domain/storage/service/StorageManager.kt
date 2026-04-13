package modeep.hear.domain.storage.service

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.global.util.generateSafeFileName
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StorageManager {
    companion object {
        private val allowedImageTypes = listOf("image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif")
        private val allowedAudioTypes = listOf("audio/mpeg", "audio/mp4", "audio/wav", "audio/webm", "video/webm", "audio/aac", "audio/ogg", "audio/x-m4a")
        private const val MAX_FILE_SIZE = 1024 * 1024 * 10L // 10MB
    }

    fun generatePath(file: FileData): String {
        validate(file)

        val uniqueFileName = "${UUID.randomUUID()}-${file.fileName!!.generateSafeFileName()}"
        val path = "${file.type.folder}/${file.userId}/$uniqueFileName"

        return path
    }

    fun generateAudioPath(file: FileData): String {
        validateAudio(file)

        val uniqueFileName = "${UUID.randomUUID()}-${file.fileName!!.generateSafeFileName()}"
        val path = "${file.type.folder}/${file.userId}/$uniqueFileName"

        return path
    }

    fun validate(request: FileData) {
        if (request.fileName.isNullOrBlank() || request.contentType.isNullOrBlank()) {
            throw BusinessException(StorageErrorCode.INVALID_FILE)
        }
        if (!allowedImageTypes.contains(request.contentType.lowercase())) {
            throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION)
        }
        if (request.size > MAX_FILE_SIZE) {
            throw BusinessException(StorageErrorCode.FILE_TOO_LARGE)
        }
    }

    fun validateAudio(request: FileData) {
        if (request.fileName.isNullOrBlank() || request.contentType.isNullOrBlank()) {
            throw BusinessException(StorageErrorCode.INVALID_FILE)
        }
        if (!allowedAudioTypes.contains(request.contentType.lowercase())) {
            throw BusinessException(StorageErrorCode.NOT_ALLOW_EXTENSION)
        }
        if (request.size > MAX_FILE_SIZE) {
            throw BusinessException(StorageErrorCode.FILE_TOO_LARGE)
        }
    }
}
