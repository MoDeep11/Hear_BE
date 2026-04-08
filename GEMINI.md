# Hear Project - Gemini CLI Instructions

이 파일은 Hear 프로젝트의 아키텍처, 기술 스택, 코딩 컨벤션 및 개발 워크플로우를 정의합니다. Gemini CLI는 이 지침을 최우선으로 준수해야 합니다.

## 1. 프로젝트 개요
Hear 프로젝트는 감정 일기 및 채팅 서비스를 제공하는 백엔드 애플리케이션입니다.

- **Language:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.5.11 (Java 21)
- **Architecture:** Hexagonal Architecture (Ports and Adapters)
- **Database:** PostgreSQL (Main), Redis (Cache/Session)
- **Migration:** Flyway
- **Build Tool:** Gradle (Kotlin DSL)

## 2. 아키텍처 가이드라인
프로젝트는 계층화된 헥사고날 아키텍처를 따릅니다. 패키지 구조를 엄격히 준수하세요.

### `modeep.hear.domain` (Domain Layer)
외부 라이브러리에 의존하지 않는 순수 비즈니스 로직 계층입니다.
- `model`: 도메인 엔티티/모델
- `service`: 비즈니스 로직을 처리하는 서비스 (Port `in` 구현)
- `port.in`: UseCase 인터페이스 (외부 -> 도메인)
- `port.out`: 외부 시스템 호출을 위한 인터페이스 (도메인 -> 외부)
- `exception`: 도메인 전용 예외 및 ErrorCode

### `modeep.hear.infrastructure` (Infrastructure Layer)
외부 시스템과의 연결을 담당하는 어댑터 및 설정 계층입니다.
- `adapter.in`: WebAdapter (REST Controller) 및 관련 DTO
- `adapter.out.persistence`: DB 연동 (Repository, JPA Entity, Mapper, Adapter)
- `config`: Spring Bean 설정, 보안(Security), 외부 서비스(AWS, Redis 등) 설정
- `external`: 외부 API 연동 (OpenFeign, S3 등)

### `modeep.hear.global` (Global Layer)
공통 관심사를 처리하는 계층입니다.
- `error`: 전역 예외 처리 및 공통 ErrorCode
- `common`: 공통 응답 구조 (`ApiResult`) 및 유틸리티

## 3. 코딩 컨벤션
- **Naming:** 
  - Port 인터페이스: `SomethingPort` (out), `SomethingUseCase` (in)
  - Adapter 구현체: `SomethingPersistenceAdapter`, `SomethingWebAdapter`
  - Service 구현체: `SomethingService`
- **Dependency Injection:** 필드 주입 대신 생성자 주입을 사용하세요.
- **Null Safety:** Kotlin의 Null Safety 기능을 적극 활용하고, `!!` 연산자 사용을 지양하세요.
- **DTO Mapping:** `domain` 모델과 `infrastructure` 엔티티 간의 변환은 `MapStruct`를 사용하여 Mapper 인터페이스를 정의하세요.
- **QueryDSL:** 복잡한 쿼리는 QueryDSL을 사용하여 구현하세요.
- **Validation:** `jakarta.validation` 어노테이션을 사용하여 DTO 레벨에서 검증을 수행하세요.
- **Error Handling:** `BusinessException`과 정의된 `ErrorCode`를 사용하여 예외를 던지세요.

## 4. 테스트 전략
- JUnit 5를 사용하여 단위 테스트 및 통합 테스트를 작성하세요.
- `testImplementation(libs.spring.boot.starter.test)`를 활용하세요.
- 새로운 기능 추가 시 반드시 해당 기능에 대한 테스트 코드를 포함해야 합니다.

## 5. 커밋 메시지 컨벤션
Conventional Commits 형식을 따르세요: `<type>(<scope>): <subject> #<issue>`
- `feat`: 새로운 기능
- `fix`: 버그 수정
- `chore`: 빌드 업무 수정, 패키지 매니저 설정 등
- `docs`: 문서 수정
- `refactor`: 코드 리팩토링
- `test`: 테스트 추가
- 예: `fix(auth): 유효하지 않은 토큰 예외 처리 수정 #73`

## 6. 주요 명령어
- **Build:** `./gradlew build`
- **Test:** `./gradlew test`
- **Lint Check:** `./gradlew ktlintCheck`
- **Lint Format:** `./gradlew ktlintFormat`
- **Run:** `./gradlew bootRun`

## 7. 기타 주의사항
- `flyway`를 사용하므로 DB 스키마 변경 시 `src/main/resources/db/migration`에 적절한 SQL 파일을 추가해야 합니다.
- `application.yml`의 민감한 정보는 환경 변수(`env`)로 관리하며, 로컬 실행 시에는 적절한 프로파일을 사용하세요.
- Husky가 설정되어 있어 커밋 전 `ktlint` 체크가 수행됩니다.
