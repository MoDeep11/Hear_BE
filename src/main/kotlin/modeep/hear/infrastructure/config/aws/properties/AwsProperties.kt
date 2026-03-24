package modeep.hear.infrastructure.config.aws.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.cloud.aws")
data class AwsProperties(
    val s3: S3,
    val region: RegionConfig,
    val credentials: Credentials
) {
    data class S3(val bucket: String)
    data class RegionConfig(val static: String)
    data class Credentials(val accessKey: String, val secretKey: String)
}
