# 든든한 한끼
### 프로젝트 계획 이유
> 집에 남아 있는 음식 재료로 만들 수 있는 음식이나 먹고 싶은 음식의 레시피를 검색할 수 있는 페이지를 제공하고자 한다.  
> 또한, 음식은 개인 취향에 따라 선호도가 다르므로 이용자의 특성을 분류하여 그에 따른 추천음식을 제공한다.
------------------------
## 페이지 소개
### 1. 메인
- 웹 소개
- Content 배너(식품MBTI, 레시피검색, Q&A)

### 2. 식품 MBTI
- 설문지
    - 성별, 연령, 40문제를 작성하여 모델에 전달
    - [랜덤포레스트 모델 선택](https://github.com/mongsik2/FoodMbti)
    - 도커안에 있는 플라스크 서버를 통해 통신
- 결과
    - 식품 MBTI 유형, 내용 출력
    - 추천 레시피 출력 후 클릭 시 해당 음식명 검색 결과로 이동

### 3. 레시피 검색
- 재료명으로 검색
    - 네이버 카테고리를 통해 JSON형태로 api통해 식약처과 통신
    - 결과 사진, 음식명, 조리방법이 나오며 100개 리스트 출력
    - [식약처 API](http://www.foodsafetykorea.go.kr/api/openApiInfo.do?menu_grp=MENU_GRP31&menu_no=661&show_cnt=10&start_idx=1&svc_no=COOKRCP01)
- 음식명으로 검색
    - JSON형태로 네이버 블로그 api통해 통신
    - 작성자, url, 제목, 작성일이 100개 리스트 출력
    - [네이버 블로그 API](https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EA%B0%9C%EC%9A%94)

### 4. 게시판
- 게시글 리스트 보기
- 게시글 작성 및 수정
- 댓글 작성 및 삭제

### 5. 대시보드
- 최다 검색 리스트
    1. 최다 검색 음식명 리스트(일주일(지난 주) 기준) - 전체
    2. 최다 검색 음식명 리스트(일주일(지난 주) 기준)  - MBTI 기준(사용자 MBTI에 따라 출력)
- 네이버 API를 통한 검색어 트렌드 - 검색어 3개 차이 비교
    1. 상위 3위 검색어의 최근 3개월의 통합 검색어 클릭 추이 그래프
    2. 상위 3위 검색어의 최근 3개월의 통합 성별 그래프
    3. 상위 3위 검색어의 최근 3개월의 통합 연령별 그래프

### 6. 마이페이지
- 정보 업데이트

### 7. 관리자페이지
- 사용자 관리
    - 사용자 현황파악
    - 사용자 권한 수정 및 삭제
- 통계
    - 식품 MBTI 작성 사용자 비율
- 게시판

### 8. 로그인 및 회원가입
- 로그인
    - 카카오 Oauth
    - 구글 Oauth
    - 네이버 Oauth
    - 일반 auth
- 회원가입
-----------------
## 결과
### 1. 메인
![main](https://user-images.githubusercontent.com/104689908/194231569-91374d34-8121-4a89-9c10-86812a93b3ef.png)
### 2. 대시보드
![dashboard](https://user-images.githubusercontent.com/104689908/194231882-7c258b2c-ba9e-4f63-a6f3-4a7edd1b9eea.png)
### 3. 식품 MBtI
#### - 설문지
![식품MBTI_Survey](https://user-images.githubusercontent.com/104689908/194232099-4533065a-2b98-4724-b413-1b687e604070.PNG)
#### - 결과
![식품MBTI_Result](https://user-images.githubusercontent.com/104689908/194232186-5af8d565-f1bc-4b0f-91de-2b1ff9e338be.PNG)
### 4. 레시피 검색
#### - 음식명으로 검색
![음식명검색](https://user-images.githubusercontent.com/104689908/194232255-0b5eeb85-4c6e-4de2-9795-c18bbfcdb71d.PNG)
#### - 재료명으로 검색
![재료명으로검색](https://user-images.githubusercontent.com/104689908/194232329-cc97ef9c-951d-445c-a7ca-4619d1178018.PNG)
-----------------
## 참고
### application.yml
    debug: true

    server:
      port: 8080
      servlet:
        context-path: /
        encoding:
          charset: UTF-8
          enabled: true
          force: true

    spring:
      mvc:
        hiddenmethod:
          filter:
            enabled: true

      datasource:
        driver-class-name: org.mariadb.jdbc.Driver
        url: jdbc:mariadb://localhost:3306/meal?serverTimezone=Asia/Seoul
        username: DB명
        password: DB비번

      sql:
        init:
          mode: always
          data-locations:
            - classpath:db/data.sql

      jpa:
        open-in-view: true
        hibernate:
          ddl-auto: create  # create update
          naming:
            physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
          use-new-id-generator-mappings: false
        show-sql: true
        defer-datasource-initialization: true
        properties:
          hibernate.format_sql: true

      jackson:
        serialization:
          fail-on-empty-beans: false

      security:
        oauth2:
          client:
            registration:
              google:
                clientId: 구글 Client ID
                clientSecret: 구글 Client PW
                scope:
                  - email
                  - profile

              naver:
                clientId: 네이버 Client ID
                clientSecret: 네이버 Client PW
                scope:
                  - name
                  - email
                  - gender
                  - age
                clientName: Naver
                authorizationGrantType: authorization_code
                redirectUri: http://localhost:8080/login/oauth2/code/naver

              kakao:
                clientId: 카카오 Client ID
                scope:
                  - profile_nickname
                  - account_email
                  - gender
                  - age_range
                clientName: Kakao
                authorizationGrantType: authorization_code
                clientAuthenticationMethod: POST
                redirectUri: http://localhost:8080/login/oauth2/code/kakao

            provider:
              naver:
                authorizationUri: https://nid.naver.com/oauth2.0/authorize
                tokenUri: https://nid.naver.com/oauth2.0/token
                userInfoUri: https://openapi.naver.com/v1/nid/me
                userNameAttribute: response
              kakao:
                authorizationUri: https://kauth.kakao.com/oauth/authorize
                tokenUri: https://kauth.kakao.com/oauth/token
                userInfoUri: https://kapi.kakao.com/v2/user/me
                userNameAttribute: id

    myinfo:
      naverApi:
        naverClientId: 네이버 Client ID
        naverClientSecret: 네이버 Client PW

      recipeApiService:
        key: 식약처 key
        serviceName: 식약처 PW
