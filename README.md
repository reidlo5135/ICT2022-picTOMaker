# **픽토메이커(22_HF168)**
2022년 한이음 ICT 멘토링 프로젝트<br><br>
2022-03-02 ~ <br>
멘토: 오은석<br>
멘티: 진승범, 강준모, 김동호, 서동근<br>
<b>VERSION: 1.1.0</b><br>
<b><a href="https://github.com/reidlo5135/ICT2022-picTOMaker-v2.0.0">-> Node-Express.js Version(v2.0.0)</a></b><br>
<b><a href="https://github.com/reidlo5135/ICT2022-picTOMaker-v2.0.0/tree/dev-2.1.0">-> Node-Express.ts Version(v2.1.0)</a></b><br>

## **픽토메이커란?**
<b>픽토그램(pictogram)</b>이란 그림문자 또는 픽토는 의미하고자 하는 바를 직접적으로 묘사한 그림을 통해 의미를 전달하는 표의 문자입니다. <br><br>
<b>픽토메이커</b>는 사용자가 원하는 <b>픽토그램(pictogram)</b>을 사용자가 직접 촬영 및 제작 후 사용 및 공유/배포를 가능하게 하는 웹 서비스입니다. <br><br>

## <b>사용기술</b>
<span><img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"></span>
<img src="https://img.shields.io/badge/Swagger-6DB33F?style=for-the-badge&logo=Swagger&logoColor=white"></span>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
<img src="https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"><br>
<img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
<img src="https://img.shields.io/badge/react-0769AD?style=for-the-badge&logo=react&logoColor=White">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git kraken-179287?style=for-the-badge&logo=gitkraken&logoColor=white">
<img src="https://img.shields.io/badge/GRADLE-efefef?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/AMAZON AWS-232F3E?style=for-the-badge&logo=amazon AWS&logoColor=white"><br>
<img src="https://img.shields.io/badge/AMAZON rds-527FFF?style=for-the-badge&logo=amazon rds&logoColor=white">
<img src="https://img.shields.io/badge/AMAZON s3-569A31?style=for-the-badge&logo=amazon s3&logoColor=white">
<img src="https://img.shields.io/badge/AMAZON ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"><br>
<img src="https://img.shields.io/badge/Adobe XD-FF61F6?style=for-the-badge&logo=Adobe XD&logoColor=white">
<img src="https://img.shields.io/badge/Intellij IDEA-000000?style=for-the-badge&logo=IntelliJ Idea&logoColor=white">
<img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=for-the-badge&logo=Visual Studio Code&logoColor=white"></span>

- Design - Figma, Adobe Xd
- Frontend - React.js, HTML5, CSS3, JavaScript, MediaPipe, TensorFlow
- Backend - Spring Boot, Spring Security, OAuth2.0, REST API, Swagger, Json Web Token, JAVA(11)
- Database - MySQL, Spring Data JPA, AWS RDS, AWS S3
- WAS - Apache Tomcat, AWS EC2
- Version Control - Git, GitHub, Git Kraken
- IDE - IntelliJ IDEA Ultimate, Visual Studio Code

## <b>주요기능</b>

### [서비스 주요 기능]
- <b>인체 인식</b> : 픽토그램 촬영 시, 카메라를 통해 사람의 얼굴, 손, 몸을 인식하여 해당 정보 기반의 픽토그램 그래픽을 매핑하여 구성
- <b>사물 인식</b> : 픽토그램 촬영 시, 카메라를 통해 사용자가 비춘 사물을 인식하여 해당 사물에 대한 픽토그램 그래픽 제공
- <b>픽토그램 편집</b> : 픽토그램 촬영 전, 촬영 후에 픽토그램을 사용자가 직접 디자인 할 수 있는 환경 제공
- <b>애니메이션 플랫폼</b> : 정적인 픽토그램에 국한된 것이 아니라, 짧은 애니메이션 형태의 픽토그램 또한 제공
- <b>픽토그램 디자인</b> : 사용자가 직접 디자인한 신체 부위 기반의 픽토그램 제작 기능
- <b>커뮤니티</b> : 자신이 만들거나 디자인한 픽토그램을 해당 서비를 통해 공유할 수 있고, SNS를 통해서도 공유 가능(OAuth2.0 - 소셜 로그인 제공)


### [관리자 기능] - 별도 페이지 X(Swagger로 관리)
- 회원(토큰) 관리
- 픽토그램 등록, 수정, 삭제, 검열
- 커뮤니티 게시판 관리 기능
- 문의사항 조회 및 접수
