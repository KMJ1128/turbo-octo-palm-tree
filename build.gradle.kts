plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.longtoast"
version = "0.0.1-SNAPSHOT"
description = "Spring Boot File Upload Minimal Setup"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// 💥 repositories 블록이 삭제됨 💥

dependencies {
    // 웹 기능 및 파일 업로드 처리
    implementation("org.springframework.boot:spring-boot-starter-web")

    // ✅ WebClient 사용을 위한 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // HTML 템플릿 엔진
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Lombok (선택 사항)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // 테스트 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 💥 Retrofit 의존성 삭제됨 💥
}

tasks.withType<Test> {
    useJUnitPlatform()
}