package modeep.hear.infrastructure.adapter.out.s3

import modeep.hear.domain.s3.exception.AwsErrorCode
import modeep.hear.domain.s3.model.FileData
import modeep.hear.domain.s3.port.out.S3Port
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GeneratePresignedUrlResponse
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Component
class S3StorageAdapter(
    private val s3Presigner: S3Presigner,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties
) : S3Port {
    override fun generatePreSignedUrl(file: FileData): GeneratePresignedUrlResponse {
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
        return GeneratePresignedUrlResponse(finalUrl, file.filePath)
    }

    override fun delete(s3Url: String) {
        val key = extractKeyFromUrl(s3Url)

        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(awsProperties.s3.bucket)
            .key(key)
            .build()

        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: SdkException) {
            throw BusinessException(AwsErrorCode.FILE_DELETE_FAILED)
        }
    }

    private fun extractKeyFromUrl(s3Url: String): String {
        val expectedPrefix = "${awsProperties.s3.bucket}.s3.${awsProperties.region.static}.amazonaws.com/"
        return if (s3Url.contains(expectedPrefix)) {
            s3Url.substringAfter(expectedPrefix)
        } else {
            throw BusinessException(AwsErrorCode.INVALID_S3_URL)
        }
    }
}
