plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.2.2")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    compileOnly("org.jetbrains.compose.compiler:compiler:1.5.8")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
            version = "1.0"
        }
        register("androidLibrary") {
            id = "android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
            version = "1.0"
        }
    }
} 