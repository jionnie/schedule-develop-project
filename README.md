# 📆 일정 관리 CRUD 프로젝트

## ✅ 프로젝트 소개

일정 관리 CRUD 프로젝트 <br>
개발 기간: 2025.11.07~2025.11.20

## ✅ 개발 환경

- **OS**: Windows 11
- **IDE**: IntelliJ IDEA
- **Language**: Java 17
- **Build Tool**: Gradle
- **Version Control**: Git, GitHub
- **Test Tool**: Postman

## ✅ 기술 스택

- **Language**: Java
- **Framework**: Spring Boot
- **ORM**: JPA (Hibernate)
- **Database**: MySQL

## ✅ 실행 방법

- 방법 1
  - 명령 프롬프트 또는 터미널 열기
  - 프로젝트 경로로 이동
  - ```./gradlew clean build ```로 프로젝트 빌드
  - ```java -jar build/libs/scheduleDevelop-0.0.1-SNAPSHOT.jar```

- 방법 2
  - 인텔리제이에서 ```ScheduleDevelopApplication``` 실행
 
- ⚠️ application.properties에서 데이터베이스 연결 정보 확인
  
```
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_develop_db
spring.datasource.username=root
spring.datasource.password=비밀번호
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## ✅ 주요 기능

- 회원 가입
- 로그인
- 로그아웃
- 회원 삭제

- 일정 등록
- 일정 조회
  - 단 건 조회, 전체 조회
- 일정 수정
- 일정 삭제

## ✅ 프로젝트 구조

```
com.example.schedule
             ├── domain
                    ├── member
                           ├── controller
                           ├── dto
                           ├── entity
                           ├── repository
                           └── service
                    └── schedule
                           ├── controller
                           ├── dto
                           ├── entity
                           ├── repository
                           └── service
             ├── global
                    ├── common
                           ├── dto
                           ├── entity
                           └── exception
                    └── config
             └── ScheduleApplication
```

## ✅ API 명세서



## ✅ ERD 다이어그램

<img width="1026" height="336" alt="image" src="https://github.com/user-attachments/assets/6e75eeae-e658-4185-b90b-7e4aa5d4eaa7" />


