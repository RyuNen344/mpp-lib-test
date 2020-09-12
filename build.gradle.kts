plugins {
    kotlin("multiplatform") version "1.3.72"
    id("com.android.library")
    id("kotlin-android-extensions")
}

group = "com.ryunen344.mpp.lib"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    android {
        publishAllLibraryVariants()
    }
    ios() {
        binaries {
            framework()
        }
    }
    sourceSets {
        all {
            languageSettings.apply {
                languageVersion = "1.3"
                apiVersion = "1.3"
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
        val commonMain by getting
        val androidMain by getting
        val iosMain by getting
    }
}
android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets.forEach {
        it.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
    packagingOptions {
        exclude("META-INF/*")
    }
    lintOptions {
        isAbortOnError = false
    }
}
