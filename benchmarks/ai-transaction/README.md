# AI 장애 중 트랜잭션 경계 비교

일기 AI 피드백의 `조회 → 외부 호출 → 저장` 구조를 재현한 독립 실험이다.
운영 API 또는 PR #48의 과거/현재 서비스를 직접 실행한 결과가 아니다.
전체 트랜잭션 비교군은 이 실험을 위해 만든 대조군이다.

## 실행

Java 21, PostgreSQL `localhost:5432/hear_test`, 계정 `agent_hear`가 필요하다.
PowerShell에서 비밀번호를 `BENCH_DB_PASSWORD` 환경 변수에 설정한 후 저장소 루트에서 실행한다.

```powershell
./gradlew.bat -p benchmarks/ai-transaction benchmark --console=plain
```

정상 저장/실패 경계만 다시 검사하려면 `--args=--verify-only`를 추가한다. 기존 측정 CSV를 덮어쓰지 않는다.

프로덕션 의존성과 설정은 변경하지 않는다. 실험용 Gradle 프로젝트가 별도 의존성을 사용한다.
DB에는 `hear_tx_bench.diary`와 고정 UUID의 실험 행 1개를 만든다.
기존 애플리케이션 스키마에 접근하지 않으며, 종료 후 실험 행은 남긴다.
정상 저장 검사에서 실험 행을 수정하고 다시 `fixture`로 복원한다.

## 조건

- 실제 PostgreSQL, HikariCP 6.3.3, Spring ORM 6.2.16, Hibernate 6.6.42.Final.
- JDBC 드라이버 42.7.8, 풀 5개, 동시 작업 10개, 획득 타임아웃 20초.
- 로컬 HTTP AI 서버: 3초 대기 후 본문이 있는 HTTP 500.
- HTTP 타임아웃 10초, 재시도 없음. 현재 애플리케이션의 피드백 어댑터도 HTTP 500은 재시도 대상인 `WebClientRequestException`에 해당하지 않는다.
- inside: 조회, AI 호출, 저장을 하나의 `JpaTransactionManager` 트랜잭션에 포함.
- split: 조회 트랜잭션 종료 후 AI 호출, 성공한 경우에만 저장 트랜잭션 실행.
- 각 모드에서 성공 1건·실패 1건을 워밍업/검사한 뒤 측정 데이터에서 제외.
- 30건씩 3회: 모드당 90건, 전체 180건. 홀수 회차 inside 먼저, 짝수 회차 split 먼저.
- 고정 작업자 풀의 닫힌 부하 모델이다. HTTP 사용자 API 호출 수가 아니라 논리적 피드백 작업 수다.

## 계측

`DataSource.getConnection()` 직전 A, 반환 직후 B, `Connection.close()` 완료 직후 C를 `System.nanoTime()`으로 기록한다.
대기 시간은 B−A, 점유 시간은 C−B이다. 지표에는 풀의 호출/반환 오버헤드가 포함된다.
요청별 점유 시간은 해당 요청의 모든 대여 시간을 합친 값이며, 실패 실험에서는 요청당 대여가 1회인지 검증한다.
p95는 오름차순 표본의 `ceil(N × 0.95)`번째 값이다. 전체 p95는 회차별 p95 평균이 아니라 전체 원시 표본에서 계산한다.

- `results/leases.csv`: 대여별 원시 대기/점유 시간.
- `results/requests.csv`: 작업별 응답 시간, AI 호출 직전 커넥션 수·트랜잭션 상태, 종료 상태.
- `results/summary.csv`: 회차별 및 전체 요약. round=0은 전체 표본이다.

커넥션 획득 실패, 예상 밖 HTTP 상태, 누수, 표본 누락이 있으면 실험은 실패하며 요약을 정상 결과로 저장하지 않는다.
정상 저장과 실패 시 미변경, AI 호출 시 경계, 실제 HTTP 호출 수, 대여 수, p95 계산을 검사한다.

## 해석 제한

Spring JPA/Hikari/PostgreSQL을 실제 사용하지만 도메인 서비스, 실제 테이블, 인증, WebClient, 코루틴을 실행하지 않는다.
요청 ID는 동기 작업을 수행하는 스레드에만 귀속하므로 프로덕션 코루틴 계측 코드로 그대로 사용하면 안 된다.
AI 장애를 DB 트랜잭션에 포함했을 때 발생하는 점유와 대기 비용의 통제 실험이다.
작은 풀은 차이를 관찰하기 위한 조건이며 운영 서버 처리량으로 해석하지 않는다.
획득 대기 시간은 작업 예약 대기 시간과 구분된다. 애플리케이션 서버는 실행하지 않는다.
커넥션 점유가 줄어도 AI 서버 자체의 대기 시간은 그대로다.
