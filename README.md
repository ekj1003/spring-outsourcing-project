# 아웃소싱 프로젝트
## 팀 : 21세기 사람들 (14조) 👶
### 팀원 : 강이원, 정은교, 조수현, 김아름
#### 👩‍💻 역할 분담
- 강이원 : 메뉴, 검색
    
- 정은교 : 가게, 대시보드
    
- 조수현 : 주문, 리뷰
    
- 김아름 : 유저, 대시보드

**🐱‍🏍 Ground Rules**
- 캠 항상 켜놓기
- 자리 비울 때 춤추기 or 슬랙에 말하기
- 과제 중 모르는 것 있으면 바로바로 물어보기
- 새롭게 배운 정보 슬랙에 공유하기

**🎯 Goals**
1. 도전 기능 4개 이상 하기
2. 팀 컨벤션 만들어서 지키기

**📌 회의**
- 아침 회의: 10:00 ~ 10:30
- 저녁 회의: 20:00 ~ 20:30

## 프로젝트 소개
배달 어플리케이션 아웃소싱 프로젝트

### 🖼 와이어프레임
![image](https://github.com/user-attachments/assets/6fe94565-34fb-4bd2-a386-01faa975cabd)
![image](https://github.com/user-attachments/assets/543389e6-76a6-4d92-9688-b45f99dde651)
![image](https://github.com/user-attachments/assets/79cc88a9-9357-41cb-b3f1-cd19a1b6a755)

### 📑 API 명세서
https://documenter.getpostman.com/view/37562373/2sAXqwXez4

### 🛠 ERD 다이어그램
![image](https://github.com/user-attachments/assets/2f5153fb-e804-425a-97bd-a132ed9fba84)

### 🦺 트러블슈팅
#### 김아름

**필터에서 발생한 예외 처리**

- 문제점: 필터에서 발생한 예외는 @RestControllerAdvice에서 처리되지 않음.

- 원인: 필터는 컨트롤러 전에 실행되므로 예외가 컨트롤러에 도달하지 않음. 필터는 서블릿 컨테이너 내에서 동작하고, 컨트롤러에 도달하기 전에 처리되기 때문.

- 해결책: 필터 내부에서 예외를 직접 처리하거나 response.sendError()를 사용하여 에러를 클라이언트에 전달하는 방식으로 처리해야 함.

#### 조수현

**다중 상품 주문 및 장바구니 초기화 문제 해결**

- 문제점: 한 번에 여러 상품을 주문할 수 없었고, 장바구니에 남은 상품들이 계속 조회되는 문제가 발생함.  

- 해결책: 주문과 상품을 연결하는 주문 상세 테이블을 만들어 한 주문에 여러 상품을 연결함. 주문이 완료되면 장바구니에서 해당 사용자의 모든 항목을 삭제하고, 상품 정보는 주문 상세 테이블에 저장하도록 변경함.   
이로써 여러 상품을 한 번에 주문할 수 있게 되었으며, 주문 후 장바구니가 비워지는 문제가 해결됨.

#### 정은교

**월별 주문 내역 조회 기능**

- 문제점: Order의 createdAt이 LocalDateTime인데 월별 조회에서는 LocalDate로 처리하려다 타입 불일치 발생.

- 해결책: 별 조회를 위해 시작일과 마지막일을 LocalDateTime으로 변환하여 날짜 범위를 설정함.    
리포지토리에 날짜 범위를 조건으로 하는 메서드를 추가하여 특정 가게의 주문 내역을 정확하게 조회할 수 있도록 함.

#### 강이원

**인기 검색어 트러블슈팅 요약**

- 문제점: 인기 검색어의 조회수가 계속 누적되어 시간이 지나면 최신 검색어와 상관없이 이전에 많이 검색된 키워드만 상위에 표시됨.

- 해결책: 1시간마다 검색어 조회수를 초기화하는 스케줄러를 도입하여 최신 검색어를 반영할 수 있도록 수정함, 이를 통해 최신 인기 검색어 순위를 정확하게 유지할 수 있게 됨.

### 🥇 필수 요구사항
#### 1. 회원가입/로그인
- 회원가입
    - 사용자 아이디
        - 사용자 아이디는 이메일 형식이어야 합니다.
    - 비밀번호
        - 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.
        - 비밀번호는 최소 8글자 이상이어야 합니다.
    - 권한
        - 회원가입시 일반 유저(USER) 또는 사장님(OWNER)으로 가입할 수 있습니다.
        - 권한에 따라 사용할 수 있는 기능이 나뉘어집니다.
    - **⚠️ 예외처리**
        - 중복된 `사용자 아이디`로 가입하는 경우
        - `사용자 아이디` 이메일과 비밀번호 형식이 올바르지 않은 경우
- 회원탈퇴    
    - 조건
        - 탈퇴 처리 시 `비밀번호`를 확인한 후 일치할 때 탈퇴 처리합니다.
        - 탈퇴한 사용자의 아이디는 재사용할 수 없고, 복구할 수 없습니다.
    - **⚠️ 예외처리**
        - `사용자 아이디`와 `비밀번호`가 일치하지 않는 경우
        - 이미 탈퇴한 `사용자 아이디`인 경우
- 로그인
    - 가입한 아이디와 비밀번호로 로그인 합니다.
 
#### **2. 가게**

- 가게 생성/수정
    - 가게는 오픈 및 마감 시간이 있습니다.
    - 가게는 최소 주문 금액이 있습니다.
    - **⚠️ 예외처리**
        - 사장님 권한을 가진 유저만 가게를  만들 수 있습니다.
        - 사장님은 가게를 최대 3개까지만 운영할 수 있습니다.
- 가게 조회
    - 고객은 가게명으로 가게를 다건 찾아볼 수 있습니다.
        - 가게 다건 조회시에는 메뉴 목록을 함께 볼 수 없습니다.
    - 가게 단건 조회 시 등록된 메뉴 목록도 함께 볼 수 있습니다.
- 가게 폐업
    - 폐업시, 가게의 상태만 폐업 상태로 변경됩니다.
        - 가게 조회 시 나타나지 않습니다.
        - 사장님은 가게를 추가로 등록할 수 있게 됩니다.

#### **3.  메뉴**

- 메뉴 생성/수정
    - 메뉴 생성, 수정은 사장님만 할 수 있습니다.
    - 사장님은 본인 가게에만 메뉴를 등록할 수 있습니다.
- 메뉴를 단독으로 조회할 수는 없으며, 가게 조회 시 함께 조회됩니다.
- 메뉴 삭제
    - 본인 가게의 메뉴만 삭제할 수 있습니다.
    - 삭제 시, 메뉴의 상태만 삭제 상태로 변경됩니다.
        - 가게 메뉴 조회 시 삭제된 메뉴는 나타나지 않습니다.
        - 주문 내역 조회 시에는 삭제된 메뉴의 정보도 나타납니다.

#### **4.  주문**

- 고객은 메뉴를 주문할 수 있습니다.
- 사장님은 주문을 수락할 수 있으며, 배달이 완료될 때까지의 모든 상태를 순서대로 변경 합니다.
- 주문 요청 및 상태 변경
    - 새로운 주문이거나 주문의 상태가 변경될 때는 AOP에 의해 로그를 남겨야합니다.
        - 로그에는 `요청 시각`, `가게 id`, `주문 id`가 필수로 포함되어야합니다.
    - **⚠️ 예외처리**
        - 가게에서 설정한 최소 주문 금액을 만족해야 주문이 가능합니다.
        - 가게의 오픈/마감 시간이 지나면 주문할 수 없습니다.

#### **5.  리뷰**

- 리뷰 생성
    - 고객은 주문 건에 대해 리뷰를 작성할 수 있습니다.
    - 리뷰는 별점을 부여합니다.(1~5점)
    - **⚠️ 예외처리**
        - `배달 완료` 되지 않은 주문은 리뷰를 작성할 수 없습니다.
- 리뷰 조회
    - 리뷰는 단건 조회할 수 없습니다.
    - 리뷰는 가게 정보를 기준으로 다건 조회 가능하며, 최신순으로 정렬합니다.
    - 리뷰를 별점 범위에 따라 조회할 수 있습니다.
        - ex) 3~5점

