# 소개
 * MyAzit는 회원들에게 각종 이벤트 제공 및 회원 커뮤니티를 제공하는 플랫폼입니다.
 * Event는 MyAzit가 제공하는 비정기적으로 제공하는 각종 행사입니다.<br>
   사이트 관리자는 Event를 생성/수정/삭제 등 관리하며, 각 이벤트를 신청한 회원을 관리할 수 있습니다.
 * Club은 MyAzit 회원들이 자발적으로 만들고 모일 수 있는 소통공간입니다. (구현중)<br>
   회원들은 사이트 관리자 승인 시 Club을 운영할 수 있으며 개별 클럽은 클럽게시판을 이용할 수 있습니다.
 * 각 회원은 Email, password, 이름 정보만 제공하면 사이트에 가입할 수 있으며, 일반회원 권한을 가집니다.<br>
   MyPage에서는 회원정보를 수정할 수 있으며 이름과 패스워드만 수정가능합니다. <br>
   신청한 이벤트 정보에서는 이벤트 신청 여부, 참가비 납부 여부 등을 확인할 수 있습니다. (구현중)<br>
   클럽 정보에서는 참가중인 클럽 정보, 내가 관리중인 클럽 여부 등을 확인할 수 있습니다. (구현중)
 
 
# 개발환경
* Intellij IDEA Community
* Java 17
* Gradle
* spring Boot 2.7.0
* Mysql

# ERD
<details>
 <summary>접기/펼치기</summary>
 
![ERD](https://user-images.githubusercontent.com/126640838/235389975-18c5eff3-f663-4b72-8d98-5b87e2fabf89.svg)
</details>


# 기술 세부 스택
 ## 백엔드
   * Spring boot
   * Spring Web
   * Spring Data JPA
   * Thymeleaf
   * Spring Security
   * Lombok
   * Spring Boot DevTools
   * H2 DB

  ## 프론트엔드
   * Jquery
   * Bootstrap
 
  ## 기타
  * Thymeleaf
  * docker

# 데모사이트 이미지 *(로컬환경에 구현된 샘플 페이지입니다.)*
<details>
 <summary>접기/펼치기</summary>
 
## 이벤트 관리자페이지
![이벤트 관리페이지](https://user-images.githubusercontent.com/126640838/235391627-4e4df600-49ce-4bc0-b6ba-822dfb5dcf60.PNG)

## 이벤트별 관리페이지
![이벤트별 관리페이지](https://user-images.githubusercontent.com/126640838/235391690-81c3bc4a-82ce-49c1-bec9-81704762c921.PNG)

## 계정정보 관리페이지
![계정관리페이지](https://user-images.githubusercontent.com/126640838/235391703-9803e76b-006b-4d7c-9871-916127e51de6.PNG)
 </details>
