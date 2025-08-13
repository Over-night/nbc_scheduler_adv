# 사용자 인증 기반 공용 스케쥴러 어플리케이션

---

## 프로젝트 개요

해당 프로젝트는 가입한 사용자 간 스케쥴을 관리하고 의견을 나눌 수 있는 플렛폼입니다.

**주요 기능**
- 사용자 세션 발급 및 확인
- 사용자(Member), 일정(Schedule), 댓글(Comment) CRUD 기능
- Validation을 통한 입력 데이터 검증
- 일정 전체 조회에 페이징을 적용

---

## 기술 스택
- Java 21
- Intellij IDEA


---

## 패키지 구조

```bash
.
|-- build
|   |-- classes
|   |   `-- java
|   |       `-- main
|   |           `-- com
|   |               `-- advanceschedular
|   |                   |-- common
|   |                   |   |-- config
|   |                   |   |-- dto
|   |                   |   |-- enums
|   |                   |   |-- exception
|   |                   |   `-- util
|   |                   |-- controller
|   |                   |-- dto
|   |                   |   |-- comment
|   |                   |   |-- member
|   |                   |   `-- schedule
|   |                   |-- model
|   |                   |-- repository
|   |                   |-- service
|   |                   `-- web
|   |                       `-- filter
|   |-- generated
|   |   `-- sources
|   |       |-- annotationProcessor
|   |       |   `-- java
|   |       |       `-- main
|   |       `-- headers
|   |           `-- java
|   |               `-- main
|   |-- reports
|   |   `-- problems
|   |-- resources
|   |   `-- main
|   |       |-- static
|   |       `-- templates
|   `-- tmp
|       `-- compileJava
|           `-- compileTransaction
|               |-- backup-dir
|               `-- stash-dir
|-- gradle
|   `-- wrapper
`-- src
    |-- main
    |   |-- java
    |   |   `-- com
    |   |       `-- advanceschedular
    |   |           |-- common
    |   |           |   |-- config
    |   |           |   |-- dto
    |   |           |   |-- enums
    |   |           |   |-- exception
    |   |           |   `-- util
    |   |           |-- controller
    |   |           |-- dto
    |   |           |   |-- comment
    |   |           |   |-- member
    |   |           |   `-- schedule
    |   |           |-- model
    |   |           |-- repository
    |   |           |-- service
    |   |           `-- web
    |   |               `-- filter
    |   `-- resources
    |       |-- static
    |       `-- templates
    `-- test
        `-- java
            `-- com
                |-- advanceschedular
                `-- example

```

---

## 실행방법

```src/main/java/com/advanceschedular/AdvanceSchedularApplication.java``` 메인 메서드 실행

---

## 주요 사용자 기능

| 기능     | 설명          |
| ------ | ----------- |
| 회원가입   | 랜덤 UUID 부여 및 비밀번호 단방향 암호화 |
| 로그인    | ID/PW 기반 로그인 및 세션 발급      |
| 스케쥴 추가 | valid 적용 |
| 스케쥴 조회 | 페이징 적용      |
| 스케쥴 수정 | 인증 필요 |
| 스케쥴 삭제 | 인증 필요 |
| 댓글 작성  | valid 적용       |
| 댓글 조회  | 상황에 따른 댓글 조회       |
| 댓글 수정  | 인증 필요       |
| 댓글 삭제  | 인증 필요       |

---

## 비고
- dotenv 라이브러리를 통한 환경변수 설정 및 application.yml에 적용 -> 클론 후 실행 시 별도의 설정 필요
- ERD Diagram : [https://www.notion.so/ERD-245889903f748011b1c2c54877563f4e?source=copy_link](https://www.notion.so/ERD-Diagram-247889903f7480dbb005d5c440214c36)
- API Document : [https://www.notion.so/API-243889903f74807ea269d968af68a2dc?source=copy_link](https://www.notion.so/API-24e889903f748038b225d146b4337b46)
