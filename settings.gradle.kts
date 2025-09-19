// settings.gradle.kts (Spring 서버)

rootProject.name = "bilbil-api"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // 🚨 카카오 SDK를 다운로드하기 위한 전용 저장소 추가 🚨
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}