# 1. 빌드 스테이지
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# 의존성 파일 먼저 복사
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew .
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 소스 복사 및 프로젝트 빌드
COPY src ./src
RUN ./gradlew build -x test -x ktlintCheck --no-daemon

# ---------------------------------------------------------
# 2. 실행 스테이지
# ---------------------------------------------------------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 유저 및 그룹 생성
RUN addgroup --system springgroup && adduser --system --ingroup springgroup springuser
RUN mkdir -p /app/logs && chown -R springuser:springgroup /app/logs

COPY --from=builder --chown=springuser:springgroup /app/build/libs/*-SNAPSHOT.jar app.jar
USER springuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]