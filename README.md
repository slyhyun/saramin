# Saramin

채용 정보를 제공하는 Backend 서버입니다. 
Jupyter Notebook과 Python을 사용하여 데이터를 크롤링한 후 MySQL을 사용하여 DB를 구축했습니다. 
그 후 Spring Boot와 JCloud를 사용하여 서버를 구축했습니다.

## 📋 프로젝트 기본 정보

- **프로젝트명**: saramin
- **목적**: 크롤링 데이터를 활용하여 채용 정보를 제공하는 서버 구축
- **주요 기능**:
    - 로그인 및 회원가입 기능
    - 프로필, 토큰 재발급 기능
    - 채용 공고 조회, 추가, 수정, 삭제 기능
    - 회사 정보 조회, 추가 수정 삭제 기능
    - 채용 공고, 회사 정보 북마크 기능
    - 채용 공고, 회사 정보 리뷰 기능
    - 지원 기능

## 🛠 기술 스택

- **백엔드**: Spring Boot
- **크롤링**: Python, Jupyter Notebook
- **데이터베이스**: MySQL
- **API**: Swagger
- **서버**: JCloud

## 🚀 설치 및 실행 가이드

프로젝트를 로컬 환경에서 실행하려면 다음 단계를 따르세요.

```bash
### 1. Jcloud 서버 접속
ubuntu 환경에서 SSH 포트로 연결

### 2. MySQL 및 Spring Boot 및 깃 설치
sudo apt-get update
sudo apt-get install mysql-server
sudo apt-get install git
sudo apt install openjdk-21-jdk

### 4. Git Clone 하기
git clone git@github.com:slyhyun/saramin.git

### 5. 빌드
./gradlew build

### 6. 서버 실행
nohup java -jar saramin-0.0.1-SNAPSHOT.jar &

```

## 📂 프로젝트 주요구조 설명
```bash
saramin/
├── src
│   ├── com.wsd.saramin
│   │   ├── apply                  # 지원
│   │   ├── bookmark               # 북마크
│   │   ├── company                # 회사
│   │   ├── config                 # 설정
│   │   ├── job                    # 채용
│   │   ├── user                   # 유저
│   │   └── util                   # 유틸
│   └── resources                  
│       └── application.properties # 프로젝트 정보
│ 
│── build.gradle                   # 패키지 파일                       
└── README.md                      # 프로젝트 설명 파일
```
## 📝  API

### 1.UserController

#### 회원가입 (POST /auth/register)
email, password, name, phone, region, age, gender를 입력하여 회원가입합니다.

#### 로그인 (POST /auth/login)
email, password를 입력하여 로그인합니다.
JWT 토큰이 반환됩니다.

#### 토큰 재발급 (POST /auth/refresh)
유효한 Refresh 토큰을 사용하여 새로운 Access 및 Refresh 토큰을 발급받습니다.

#### 회원 정보 조회 (GET /auth/profile)
email, name, phone, region, gender, password, appliedJobs, jobBookmarks, companyBookmarks가 반환됩니다.

#### 회원 정보 수정 (PUT /auth/profile)
email, name, phone, region, gender, password를 수정합니다.

#### Job 북마크 조회 (GET /auth/profile/bookmark/jobs)
id, userId, jobId, date가 반환됩니다.

#### Company 북마크 조회 (GET /auth/profile/bookmark/companies)
id, userId, companyId, date가 반환됩니다.

#### 지원 목록 조회 (GET /auth/profile/applied-jobs)
applyId, userId, jobId, jobTitle, applyDate, status가 반환됩니다.

#### Job 북마크 삭제 (DELETE /auth/profile/bookmark/job/{jobBookmarkId)
Job 북마크를 삭제합니다.

#### Company 북마크 삭제 (DELETE /auth/profile/bookmark/company/{companyBookmarkId)
Company 북마크를 삭제합니다.

---
### 2.JobController

#### 채용 공고 조회 (GET /jobs)
한 페이지에 20개의 채용 공고가 조회됩니다.
정렬, 필터링, 검색 기능이 있습니다.

#### 채용 공고 상세 조회 (GET /jobs/{id})
jobId, title, location, experience, type, education, deadline, sector, view, date, companyName, userName, link, applies, jobBookmarks, jobReviews, relatedJobs가 반환됩니다.

#### 채용 공고 등록 (POST /jobs)
title, location, experience, type, education, deadline, sector, companyName, link를 입력하여 등록합니다.

#### 채용 공고 수정 (PUT /jobs/{id})
title, location, experience, type, education, deadline, sector, companyName, link를 입력하여 수정합니다.

#### 채용 공고 삭제 (DELETE /jobs/{id})
채용 공고를 삭제합니다.

---
### 3.CompanyController

#### 회사 정보 조회 (GET /company/{id})
companyId, name, type, employee, industry, ceo, website, description, address, establishment, revenue, salary, jobs, companyBookmarks, companyReviews가 반환됩니다.

#### 회사 정보 수정 (PUT /company/{id})
name, type, employee, industry, ceo, website, description, address, establishment, revenue, salary를 입력하여 수정합니다.

---
### 4.BookmarksController

#### Job 북마크 조회 (GET /bookmarks/jobs)
id, userId, jobId, date가 반환됩니다.

#### Company 북마크 조회 (GET /bookmarks/company)
id, userId, companyId, date가 반환됩니다.

#### Job 북마크 등록, 삭제 (POST /bookmarks/jobs/{jobId})
Job 북마크가 등록, 삭제됩니다.

#### Company 북마크 등록 (POST /bookmarks/company/{companyId})
Company 북마크가 등록, 삭제됩니다.

---
### 5.JobReviewController

#### 채용 공고 리뷰 조회 (GET /jobs/{jobID}/reviews)
jobReviewId, jobId, userId, userName, content, date가 반환됩니다.

#### 채용 공고 리뷰 등록 (POST /jobs/{jobID}/reviews)
content를 입력하여 등록합니다.

#### 채용 공고 리뷰 삭제 (DELETE /jobs/{jobID}/reviews)
리뷰가 삭제됩니다.

---
### 6.JobReviewController

#### 회사 정보 리뷰 조회 (GET /companies/{companyID}/reviews)
companyReviewId, companyId, userId, userName, content, date가 반환됩니다.

#### 회사 정보 리뷰 등록 (POST /companies/{companyID}/reviews)
content를 입력하여 등록합니다.

#### 회사 정보 리뷰 삭제 (DELETE /companies/{companyID}/reviews)
리뷰가 삭제됩니다.

---
### 5.ApplyController

#### 지원 내역 조회 (GET /applications)
applyId, userID, jobID, jobTitle, applyDate, status가 반환됩니다.

#### 지원하기 (POST /applications)
지원합니다.

#### 지원 취소 (DELETE /applications/{id})
status가 PENDING일 때 지원이 취소됩니다.