# TrendHive

**TrendHive**는 실시간 트렌드 분석 및 토론을 위한 플랫폼입니다.
사용자는 최신 트렌드를 탐색하고, 이에 대한 의견을 공유할 수 있습니다.

## 🚀 프로젝트 개요

- **프로젝트명**: TrendHive
- **목적**: 실시간 트렌드 정보를 제공하고, 사용자 간 토론을 지원하는 플랫폼 구축
- **기술 스택**:
  - **백엔드**: Spring Boot, JPA, MySQL, Redis, Kafka
  - **프론트엔드**: Next.js, TypeScript, Tailwind CSS
  - **인프라**: Docker, GitHub Actions (CI/CD 예정)
- **주요 기능**:
  - 실시간 트렌드 크롤링 및 분석
  - 사용자 로그인 및 인증 (JWT 기반)
  - 댓글 및 토론 기능
  - 인기 트렌드 추천 시스템

## 📂 폴더 구조

```
TrendHive/
├── backend/                 # 백엔드 Spring Boot 프로젝트
│   ├── src/main/java/com/trendhive/backend/
│   │   ├── config/          # 보안 및 설정 파일
│   │   ├── controller/      # API 컨트롤러
│   │   ├── domain/          # 도메인 모델 (JPA 엔티티)
│   │   ├── dto/             # 요청/응답 DTO
│   │   ├── repository/      # 데이터 접근 계층
│   │   ├── service/         # 비즈니스 로직
│   │   ├── util/            # 유틸리티 클래스
│   ├── resources/
│   │   ├── application.yml  # 환경 설정 파일
│
├── frontend/                # 프론트엔드 Next.js 프로젝트
│   ├── src/
│   │   ├── components/      # UI 컴포넌트
│   │   ├── pages/           # 페이지 컴포넌트
│   │   ├── hooks/           # 커스텀 훅
│   │   ├── services/        # API 호출 로직
│   │   ├── styles/          # Tailwind CSS 스타일
│
├── docker-compose.yml        # MySQL, Redis 등 Docker 설정 파일
├── README.md                 # 프로젝트 설명
└── .gitignore                # Git에서 제외할 파일
```

## 🛠️ 환경 설정

### 1️⃣ **백엔드 실행**
```bash
cd backend
./gradlew bootRun
```

### 2️⃣ **프론트엔드 실행**
```bash
cd frontend
npm run dev
```

### 3️⃣ **Docker 실행 (MySQL & Redis)**
```bash
docker-compose up -d
```

## 🗄️ ERD (데이터 모델링)

```
User (사용자)
- id (PK)
- username
- email
- password
- created_at

Trend (트렌드)
- id (PK)
- title
- description
- source_url
- created_at

Comment (댓글)
- id (PK)
- user_id (FK)
- trend_id (FK)
- content
- created_at
```

## 🔒 인증 & 보안
- **JWT 기반 로그인 및 인증**
- **Spring Security 설정**을 통한 API 보호
- **Redis를 활용한 세션 캐싱**

## 🎯 향후 개발 목표
- 🔹 **트렌드 데이터 크롤링 기능 구현**
- 🔹 **실시간 인기 트렌드 분석 알고리즘 적용**
- 🔹 **유저 인터랙션 기능 (좋아요, 공유)**
- 🔹 **관리자 대시보드 추가**

## 📌 GitHub
```bash
git add .
git commit -m "Add new feature"
git push origin main
```

## 👥 개발자 정보
- **개발자**: @lirongzzuin
- **문의**: younggyun12@hotmail.com

