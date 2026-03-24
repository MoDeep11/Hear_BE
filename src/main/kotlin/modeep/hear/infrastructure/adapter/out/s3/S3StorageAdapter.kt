package modeep.hear.infrastructure.adapter.out.s3

import modeep.hear.domain.s3.exception.AwsErrorCode
import modeep.hear.domain.s3.model.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse
import modeep.hear.domain.s3.port.out.S3Port
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Component
class S3StorageAdapter(
    private val s3Presigner: S3Presigner,
    private val awsProperties: AwsProperties
): S3Port {
    override fun getPreSignedUrl(file: FileData): GetPresignedUrlResponse {
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
        return GetPresignedUrlResponse(finalUrl)
    }

    override fun delete(s3Url: String) {
        val key = extractKeyFromUrl(s3Url)

        // 2. 삭제 요청 객체 생성
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(awsProperties.s3.bucket)
            .key(key)
            .build()

        // 3. 실제 삭제 실행
        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: SdkException) {
            // 삭제 실패 시 비즈니스 예외로 던지거나 로그를 남깁니다.
            throw BusinessException(AwsErrorCode.FILE_DELETE_FAILED)
        }
    }

    private fun extractKeyFromUrl(s3Url: String): String {
        return s3Url.substringAfter("$endpoint/").removePrefix("/")
    }
}