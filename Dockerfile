FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# 의존성 파일 먼저 복사
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

RUN gradle build -x test -x ktlintCheck --no-daemon > /dev/null 2>&1 || true

COPY src ./src

# 프로젝트 빌드
RUN gradle build -x test -x ktlintCheck --no-daemon

# 경량화된 JRE 환경에서 실행
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN echo "Build Complete"

# 빌드 스테이지에서 생성된 jar 파일만 추출하여 복사
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]