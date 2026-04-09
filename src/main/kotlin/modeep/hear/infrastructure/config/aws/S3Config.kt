package modeep.hear.infrastructure.config.aws

import jakarta.annotation.PreDestroy
import modeep.hear.infrastructure.config.aws.properties.AwsProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
@EnableConfigurationProperties(AwsProperties::class)
class S3Config(private val awsProperties: AwsProperties) {

    private lateinit var presigner: S3Presigner

    @Bean
    fun s3Presigner(): S3Presigner {
        val credentials = AwsBasicCredentials.create(
            awsProperties.credentials.accessKey,
            awsProperties.credentials.secretKey
        )

        presigner = S3Presigner.builder()
            .region(Region.of(awsProperties.region.static))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()

        return presigner
    }

    @PreDestroy
    fun close() {
        if (::presigner.isInitialized) {
            presigner.close()
        }
    }
}
