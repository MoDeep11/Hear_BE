package modeep.hear.infrastructure.adapter.out.storage

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.domain.storage.exception.StorageErrorCode
import modeep.hear.domain.storage.port.out.StoragePort
import modeep.hear.domain.storage.service.StorageManager
import modeep.hear.domain.storage.vo.FileData
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.storage.dto.response.GenerateUploadUrlResponse
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.Delete
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest
import software.amazon.awssdk.services.s3.model.ObjectIdentifier
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

private val log = KotlinLogging.logger {}

@Component
class StoragePersistenceAdapter(
    private val s3Presigner: S3Presigner,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties,
    private val storageManager: StorageManager
) : StoragePort {

    private val bucket = awsProperties.s3.bucket
    private val region = awsProperties.region

    override fun generateUploadUrl(file: FileData): GenerateUploadUrlResponse {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(file.fileName)
            .contentType(file.contentType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(5))
            .putObjectRequest(putObjectRequest)
            .build()

        val finalUrl = s3Presigner.presignPutObject(presignRequest).url().toString()
        return GenerateUploadUrlResponse(finalUrl, file.fileName!!)
    }

    override fun getUrlToUpload(fileData: FileData): String {
        val key = storageManager.generatePath(fileData)
        return "https://$bucket.s3.$region.amazonaws.com/$key"
    }

    override fun upload(file: MultipartFile, fileData: FileData): String {
        val key = storageManager.generatePath(fileData)

        val request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(file.contentType)
            .contentLength(file.size)
            .build()

        return try {
            // .use를 사용하여 InputStream을 안전하게 닫음
            file.inputStream.use { inputStream ->
                s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(inputStream, file.size)
                )
            }
            "https://$bucket.s3.$region.amazonaws.com/$key"
        } catch (e: SdkException) {
            // SdkException을 포착하여 도메인 예외로 변환
            log.error(e) { "S3 파일 업로드 실패: ${e.message}, key: $key" }
            throw BusinessException(StorageErrorCode.FILE_UPLOAD_FAILED)
        }
    }

    override fun deleteAll(urls: List<String>) {
        if (urls.isEmpty()) return

        urls.chunked(1000).forEach { chunk ->
            val keys = chunk.map { url ->
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
                val response = s3Client.deleteObjects(deleteObjectsRequest)
                if (response.hasErrors()) {
                    response.errors().forEach { error ->
                        log.error { "S3 삭제 실패 - Key: ${error.key()}, Message: ${error.message()}" }
                    }
                }
            } catch (e: SdkException) {
                log.error { "S3 파일 삭제 실패: ${e.message}" }
                throw BusinessException(StorageErrorCode.FILE_DELETE_FAILED)
            }
        }
    }

    override fun deleteAllByKeys(keys: List<String>) {
        if (keys.isEmpty()) return

        keys.chunked(1000).forEach { chunk ->
            val objects = chunk.map { key -> ObjectIdentifier.builder().key(key).build() }

            val deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(awsProperties.s3.bucket)
                .delete(Delete.builder().objects(objects).build())
                .build()

            try {
                val response = s3Client.deleteObjects(deleteObjectsRequest)
                val errors = response.errors()
                if (errors.isNotEmpty()) {
                    errors.forEach { error ->
                        log.error { "S3 삭제 실패 - Key: ${error.key()}, Message: ${error.message()}" }
                    }
                    throw BusinessException(StorageErrorCode.FILE_DELETE_FAILED)
                }
            } catch (e: SdkException) {
                log.error { "S3 파일 삭제 실패: ${e.message}" }
                throw BusinessException(StorageErrorCode.FILE_DELETE_FAILED)
            }
        }
    }

    override fun extractKey(url: String): String = extractKeyFromUrl(url)

    private fun extractKeyFromUrl(s3Url: String): String {
        val expectedPrefix = "${awsProperties.s3.bucket}.s3.${awsProperties.region.static}.amazonaws.com/"
        return if (s3Url.contains(expectedPrefix)) {
            s3Url.substringAfter(expectedPrefix)
        } else {
            throw BusinessException(StorageErrorCode.INVALID_URL)
        }
    }
}
