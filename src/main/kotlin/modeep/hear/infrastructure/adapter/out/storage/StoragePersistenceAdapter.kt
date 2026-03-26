package modeep.hear.infrastructure.adapter.out.storage

import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Component
class StoragePersistenceAdapter(
    private val s3Presigner: S3Presigner,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties
) : StoragePort {
    override fun generateUploadUrl(file: FileData): GenerateUploadUrlResponse {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(awsProperties.s3.bucket)
            .key(file.filePath)
            .contentType(file.contentType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(5))
            .putObjectRequest(putObjectRequest)
            .build()

        val finalUrl = s3Presigner.presignPutObject(presignRequest).url().toString()
        return GenerateUploadUrlResponse(finalUrl, file.filePath)
    }

    override fun deleteAll(urls: List<String>) {
        if (urls.isEmpty()) return

        val keys = urls.map { url ->
            val key = extractKeyFromUrl(url)
            ObjectIdentifier.builder().key(key).build()
        }

        val deleteObjectsRequest = DeleteObjectsRequest.builder()
            .bucket(awsProperties.s3.bucket)
            .delete(
                Delete.builder()
                    .objects(keys)
                    .build()
            )
            .build()

        try {
            s3Client.deleteObjects(deleteObjectsRequest)
        } catch (e: SdkException) {
            throw BusinessException(StorageErrorCode.FILE_DELETE_FAILED)
        }
    }

    private fun extractKeyFromUrl(s3Url: String): String {
        val expectedPrefix = "${awsProperties.s3.bucket}.s3.${awsProperties.region.static}.amazonaws.com/"
        return if (s3Url.contains(expectedPrefix)) {
            s3Url.substringAfter(expectedPrefix)
        } else {
            throw BusinessException(StorageErrorCode.INVALID_URL)
        }
    }
}
