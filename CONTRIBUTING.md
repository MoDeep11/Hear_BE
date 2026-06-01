# Contributing Guidelines

Hear 프로젝트에 관심을 가져주셔서 감사합니다! 여러분의 기여가 프로젝트를 더욱 발전시킵니다. 기여하기 전에 아래 가이드라인을 확인해 주세요.

Thank you for considering contributing to project Hear!
Your contribution helps us grow and improve.
Before contributing, please review the guidelines below.

---

## 🚀 Get Started

1. 이 저장소를 **Fork** 합니다.
2. 로컬 환경으로 **Clone** 합니다.
3. 새로운 기능을 위한 **Issue**를 생성합니다. 
4. Issue에서 새로운 **Branch**를 생성합니다. (`feat/feature-name`)
5. 변경 사항을 **Commit** 하고 **Push** 합니다. 
6. 원본 저장소의 **Develop Branch** 로 **Pull Request (PR)**를 보냅니다.
7. PR이 review되고 merge 될 때까지 기다려주세요.


1. **Fork** this repository.
2. **Clone** into your local environment.
3. Create an **issue** for the new feature.
4. Create a new **Branch** in the Issue. (`feat/feature-name`)
5. **Commit** and **Push** your changes.
6. Send a **Pull Request (PR)** to the Develop Branch of the source repository.
7. Wait for the PR to be reviewed and merged.

---

## 📝 Commit Convention

우리 프로젝트는 가독성과 히스토리 관리를 위해 아래의 커밋 메시지 규격을 따릅니다.

Our project follows the commit message specifications below for readability and history management.

### Commit Message Specifications

```text
<type>(<scope>): <subject> #<issue_number>

<body> (optional)

<footer> (optional)
```

#### Examples

```text
feat(storage): implement image upload to S3 #43

- Add S3 service for handling multi-part file uploads
- Implement pre-signed URL generation logic
- Configure AWS credentials in application-prod.yml
```

### Type

커밋 메시지 type은 다음 중 하나를 따릅니다:

Commit message type is one of the following:

| Type | Description |
| :--- | :--- |
| **feat** | Add a new feature |
| **fix** | Fix a bug |
| **docs** | Documentation changes (README, comments, etc.) |
| **refactor** | Code refactoring (neither fixes a bug nor adds a feature) |
| **test** | Add or update tests |
| **chore** | Build tasks, package manager updates, code formatting, etc. |

### 🔍 Scope (Optional)

Scope는 영향을 받는 코드베이스의 정확한 부분을 지정하는 데 사용됩니다.

The scope is used to specify the exact part of the codebase affected by the change. It should be wrapped in parentheses after the type.

#### **Common Scopes**
- **Domain/Module**: `auth`, `user`, `diary`, `chat`
- **Infrastructure**: `database`, `storage`, `api`, `security`, `config`
- **DevOps**: `docker`, `ci-cd`, `github-actions`

#### **Examples**
- `feat(auth): add JWT provider for login #5`
- `fix(database): resolve connection pool leak #20`
- `refactor(storage): optimize multi-part upload logic #38`
- `docs(readme): update installation guide #43`

## 💻 Code Style
- Language: Kotlin

- Naming: 
  - Class: PascalCase
  - variable, method: camelCase

- Format: ktlint
  - Before commit: `./gradlew ktlintFormat`

## ✅ Pull Request

- PR 제목은 이슈 제목과 동일하게 작성합니다.

- 변경된 사항에 대한 간단한 설명을 포함해 주세요.



- Write the PR title the same as the issue title.

- Include a brief description of the changes.

---
문의 사항이 있다면 Issue 탭을 통해 남겨주세요!

If you have any queries, please leave them via the Issue tab!