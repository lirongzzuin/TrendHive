# TrendHive

TrendHive는 실시간 트렌드 분석 및 토론을 위한 플랫폼입니다.  
사용자는 최신 트렌드를 탐색하고 자유롭게 의견을 공유할 수 있습니다.

---

## 프로젝트 개요

- 프로젝트명: **TrendHive**
- 목적: 실시간 트렌드 정보를 수집·공유하고 사용자 간 토론을 지원하는 커뮤니티 플랫폼 구축
- 기술 스택:
  - 백엔드: Spring Boot, JPA, MySQL, Redis, Kafka
  - 프론트엔드: Next.js, TypeScript, Tailwind CSS
  - 인프라: Docker, GitHub Actions (CI/CD 예정)
- 주요 기능:
  - 트렌드 크롤링 및 분류
  - JWT 기반 사용자 인증
  - 트렌드 등록 및 댓글 작성
  - 인기 트렌드 추천 시스템
  - Swagger를 활용한 API 문서 자동 생성 및 UI 제공

---

## 폴더 구조

```
TrendHive/
├── backend/                 
│   ├── src/main/java/com/trendhive/backend/
│   │   ├── config/          
│   │   ├── controller/      
│   │   ├── domain/          
│   │   ├── dto/             
│   │   ├── repository/      
│   │   ├── service/         
│   │   ├── util/            
│   ├── resources/
│   │   ├── application.yml
│
├── frontend/                
│   ├── src/
│   │   ├── app/
│   │   ├── pages/
│   │   │   ├── trend/
│   │   │   │   └── [id].js
│   │   ├── components/      
│   │   ├── hooks/           
│   │   ├── services/        
│   │   ├── styles/
│
├── docker-compose.yml        
├── README.md                 
└── .gitignore                
```

---

## 실행 방법

### 백엔드 실행
```bash
cd backend
./gradlew bootRun
```

### 프론트엔드 실행
```bash
cd frontend
npm install
npm run dev
```

### Docker 실행 (MySQL 및 Redis 포함)
```bash
docker-compose up -d
```

---

## 테스트 환경

통합 테스트는 H2 인메모리 데이터베이스와 JWT 인증 흐름을 기반으로 구성되어 있습니다.

- 테스트 프로파일: `application-test.properties`
- DB: H2 (자동 생성 및 삭제)
- 프레임워크: Spring Boot Test, MockMvc, JUnit 5
- 주요 테스트 대상:
  - `UserController`: 회원가입, 로그인 및 JWT 발급
  - `TrendController`: 인증 기반 트렌드 등록
  - `CommentController`: 댓글 등록 및 조회

### 테스트 실행
```bash
./gradlew test
```

---

## 데이터 모델

```
User
- id
- username
- email
- password
- created_at

Trend
- id
- title
- description
- category
- created_by
- created_at

Comment
- id
- user_id
- trend_id
- content
- created_at
```

---

## 인증 및 보안

- JWT 기반 인증 토큰 발급 및 검증
- Spring Security를 활용한 접근 제어
- Redis를 통한 세션 캐싱 및 확장성 고려

---

## 향후 계획

- 트렌드 수집을 위한 외부 크롤링 모듈 구현
- 실시간 인기 트렌드 분석 알고리즘 도입
- 사용자 반응 기반 인터랙션 기능 (좋아요, 공유 등)
- 관리자 전용 대시보드 개발

---

## Git 사용 예시

```bash
git add .
git commit -m "Add new feature"
git push origin main
```

---

## 개발자 정보

- 개발자: @lirongzzuin  
- 이메일: younggyun12@hotmail.com
