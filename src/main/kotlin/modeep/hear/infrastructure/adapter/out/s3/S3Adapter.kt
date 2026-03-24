package modeep.hear.infrastructure.adapter.out.s3

import modeep.hear.domain.s3.model.FileData
import modeep.hear.infrastructure.adapter.`in`.s3.dto.response.GetPresignedUrlResponse
import modeep.hear.domain.s3.port.out.S3Port
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

class S3Adapter(
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
        TODO("Not yet implemented")
    }
}