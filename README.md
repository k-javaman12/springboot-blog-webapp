# 📋 프로젝트 개요

## 🌐 환경
- **통합 개발 환경 (IDE)**: IntelliJ Idea
- **데이터베이스 디자인**: MySQL Workbench
- **기타 도구**: Notion

## 🛠️ 기술 스택
- **프론트엔드**:
    - HTML
    - Bootstrap
    - Thymeleaf
- **백엔드**:
    - Java
- **데이터베이스**:
    - MySQL
- **빌드 도구**:
    - Gradle

## 🗺️ 데이터베이스 다이어그램
![Description](schema.png)

## 📚 프로젝트에서의 주요 학습 포인트
1. **Spring Boot와 JPA 사용**: CRUD 앱의 개발에 Spring Boot와 JPA 활용.
2. **역할 기반 접근 제어**: Spring Security를 이용하여 다양한 사용자 역할 (ADMIN, GUEST)에 따른 게시글 접근 제한 기능 구현.
    - ADMIN: 모든 게시글 접근 가능
    - GUEST: 자신이 작성한 게시글만 볼 수 있음
3. **커스텀 에러 페이지**: 403, 404, 500 등의 HTTP 상태 코드에 대응하는 사용자 친화적인 에러 페이지 디자인 및 구현.
4. @ManyToOne 어노테이션을 이용하여 일대다 및 다대일 관계를 성공적으로 구현하였다.
5. JWT를 사용하여 안전한 사용자 인증을 구현. 🌐🔐🚧📋📦🗃️

# 📝 Spring Boot 블로그 애플리케이션에 대한 문서

## 🌐 개요
이 문서는 Spring Boot 기반의 블로그 애플리케이션을 개요와 주요 기능, 구성 요소 및 기능에 대한 자세한 설명을 제공합니다. 이 애플리케이션은 JWT를 사용하여 인증하며 CRUD 작업에 대한 Spring Data JPA 및 인가를 위한 Spring Security를 활용합니다.

## 📝 주요 기능
- **사용자 인증 및 권한 부여**: 안전한 사용자 인증에 JWT를 활용하고 역할 기반 인가에 Spring Security를 사용합니다. 🌐
- **블로그 포스트 관리**: 사용자가 블로그 포스트를 생성, 보기, 편집 및 삭제할 수 있습니다. 📝
- **댓글 관리**: 사용자는 포스트에 댓글을 추가하고 댓글을 보거나 관리할 수 있습니다. 💬
- **검색 기능**: 특정 기준에 따라 블로그 포스트를 검색할 수 있는 기능을 제공합니다. 🔍
- **역할 기반 접근 제어**: 관리자 및 게스트 사용자에 대한 별도의 접근 수준이 있습니다. 👤

# 🚀 기술적 구성 요소

###  1. 보안 구성 (`WebSpringSecurity`)
- **비밀번호 인코딩**: 안전한 비밀번호 저장을 위해 `BCryptPasswordEncoder`를 사용합니다. 🔐
- **보안 필터 체인**: CSRF 보호, 폼 로그인 및 로그아웃 기능을 설정하는 HTTP 보안을 구성합니다. 🚧
- **UserDetailsService 통합**: 사용자 세부 정보 검색을 위한 사용자 지정 구현을 제공합니다. 📋
- **사용자 인증 성공 핸들러**: 성공적인 로그인 후 사용자 역할에 따라 사용자를 리디렉션합니다. 🚀

###  2. 컨트롤러 클래스
- **`AdminCommentController` 및 `AdminPostController`**: 모든 댓글과 포스트를 검토하는 관리자별 기능을 관리합니다. 🕵️‍♂️
- **`AuthController`**: 사용자 등록 및 로그인 작업을 처리합니다. 🤝
- **`BlogController`**: 주요 블로그 기능을 관리하며 포스트 보기 및 검색을 포함합니다. 📚
- **`CommentController`**: 사용자가 포스트에 댓글을 추가할 수 있도록 합니다. 💬
- **`GuestCommentController` 및 `GuestPostController`**: 게스트 사용자가 포스트 및 댓글과 상호 작용할 수 있도록 합니다. 🌐

###  3. 데이터 전송 객체 (DTO)
- **`CommentDto` 및 `PostDto`**: CRUD 작업을 위해 애플리케이션과 클라이언트 간의 데이터 전송을 담당합니다. 📤📥

###  4. 엔티티 클래스
- **`Comment` 및 `Post` 엔티티**: 데이터베이스 상호 작용을 위한 JPA 주석이 포함된 댓글 및 포스트 데이터 모델을 나타냅니다. 📦
- **`Role` 및 `User` 엔티티**: 사용자 인증 및 권한을 위해 사용자 역할 및 사용자 세부 정보를 정의합니다. 👤

###  5. 레포지토리
- **`CommentRepository` 및 `PostRepository`**: 댓글 및 포스트에 대한 CRUD 작업을 위해 `JpaRepository`를 확장합니다. 🗃️
- **`RoleRepository` 및 `UserRepository`**: 사용자 및 역할 데이터 검색 및 조작을 위한 데이터베이스와 상호 작용합니다.

### 6. 서비스 구현
- **`CommentServiceImpl` 및 `PostServiceImpl`**: 댓글과 포스트에 대한 비즈니스 로직을 제공하기 위해 각각의 인터페이스를 구현합니다. 🛠️
- **`UserDetailsServiceImpl` 및 `UserServiceImpl`**: 상세 사용자 정보 및 사용자 관련 기능을 제공합니다. 🧑‍💻

### 7. 유틸리티 클래스
- **`CommentMapper` 및 `PostMapper`**: DTO와 엔티티 간의 변환을 용이하게 합니다. 🔄
- **`SecurityUtils` 및 `UrlUtils`**: 보안 관련 작업과 URL 형식 지정을 위한 유틸리티 메서드를 제공합니다. 🔒
- **`UserDetailsImpl`**: Spring Security를 위해 `UserDetails`를 구현합니다. 🤖

### 8. JWT 구성 요소
- **`AuthTokenFilter`**: HTTP 요청에 대한 JWT 필터링을 관리합니다. 🌐
- **`JwtUtil`**: JWT 생성, 유효성 검사 및 사용자 이름 추출을 처리합니다. 🎫

## 🌐 애플리케이션 흐름
1. **사용자 등록 및 인증**: 사용자는 `AuthController`를 통해 등록하고 로그인합니다.
2. **역할 기반 액세스**: 사용자 역할(관리자 또는 게스트)에 따라 `AdminPostController` 또는 `GuestPostController`와 같은 컨트롤러에 의해 다른 기능에 액세스할 수 있습니다.
3. **블로그 포스트 상호 작용**: 사용자는 `BlogController`와 `PostServiceImpl`을 통해 포스트 보기, 생성, 편집 및 삭제를 수행할 수 있습니다.
4. **댓글 작성**: 사용자는 `CommentController`와 `CommentServiceImpl`을 통해 포스트에 댓글을 추가하거나 관리할 수 있습니다.
5. **보안**: 애플리케이션은 엔드포인트를 보호하고 사용자의 인증 및 인가를 `WebSpringSecurity`와 JWT를 통해 관리합니다.

## 📝 결론
이 Spring Boot 블로그 애플리케이션은 보안, 데이터 관리 및 사용자 상호 작용과 같은 모던 웹 애플리케이션 기능의 종합적인 구현을 보여줍니다. 모듈식 설계와 Spring Boot의 강력한 기능 활용으로 블로그 관리에 견고하고 확장 가능한 솔루션을 제공합니다.

