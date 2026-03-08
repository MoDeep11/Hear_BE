# 1. 빌드 스테이지: Gradle을 사용하여 애플리케이션 빌드
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# 빌드 효율을 위해 의존성 파일을 먼저 복사 (캐시 활용)
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# QueryDSL QClass 생성 및 프로젝트 빌드 (테스트는 제외)
RUN gradle clean build -x test --no-daemon

# 2. 실행 스테이지: 경량화된 JRE 환경에서 실행
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 추출하여 복사
# (파일명은 프로젝트 설정에 따라 다를 수 있으니 확인이 필요합니다)
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# 컨테이너가 사용할 포트 설정
EXPOSE 8080

# 애플리케이션 실행 (성능 최적화 옵션 포함)
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]