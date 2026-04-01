plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ktlint)
}

group = "modeep"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // bom
    implementation(platform(libs.spring.cloud.bom))
    implementation(platform(libs.sentry.bom))

    // kotlin
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)

    // logger
    implementation(libs.kotlin.logging)

    // Spring Boot Starters
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.security)
    implementation(libs.spring.boot.validation)
    implementation(libs.jpa)
    implementation(libs.redis)
    implementation(libs.cache)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.actuator)
    implementation(libs.kotlinx.coroutines.reactor)
    developmentOnly(libs.spring.boot.devtools)

    // jwt
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)

    // Database & Tools
    runtimeOnly(libs.postgresql)
    developmentOnly(libs.docker.compose)
    annotationProcessor(libs.configuration.processor)

    // MapStruct
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)

    // QueryDSL
    implementation(variantOf(libs.querydsl.jpa) { classifier("jakarta") })
    kapt(variantOf(libs.querydsl.apt) { classifier("jakarta") })

    // Q 클래스 생성 APT
    kapt(libs.jakarta.persistence.api)
    kapt(libs.jakarta.annotation.api)

    // spring retry & aspect
    implementation(libs.spring.retry)
    implementation(libs.spring.aspects)

    // Flyway
    implementation(libs.flyway.core)
    runtimeOnly(libs.flyway.database.postgresql)

    // Cloud & External APIs
    implementation(libs.aws)
    implementation(libs.aws.s3)
    implementation(libs.openapi)
    implementation(libs.spring.cloud.openfeign)
    // implementation(libs.spring.cloud.starter.loadbalancer)

    // Monitoring
    implementation(libs.sentry)

    // Test
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}

sourceSets {
    named("main") {
        java.srcDirs("build/generated/source/kapt/main")
    }
}

plugins.withId("org.jlleitschuh.gradle.ktlint") {
    tasks.named("runKtlintCheckOverMainSourceSet") {
        dependsOn("kaptKotlin")
    }
}

tasks.test {
    enabled = false
}
