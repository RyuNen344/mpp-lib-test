plugins {
    kotlin("multiplatform") version "1.3.72"
    kotlin("native.cocoapods") version "1.3.72"
    id("com.android.library")
    id("kotlin-android-extensions")
    id("maven-publish")
}

group = "com.ryunen344.mpp.lib"
version = "1.2.5-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "CocoaPods Test of Kotlin/Native module"
        homepage = "https://github.com/RyuNen344/mpp-lib-test"

        // You can change the name of the produced framework.
        // By default, it is the name of the Gradle project.
        frameworkName = "mpp-lib"
    }
    android {
        publishAllLibraryVariants()
    }
    ios()
    iosArm64("iosArm64Dev") {
        compilations["main"].cinterops.create("key") {
            defFile(project.file("src/nativeInterop/cinterop/ntstr.def"))
            includeDirs(project.file("src/include"))
            packageName("com.ryunen344.mpp.lib.nt")
            compilerOpts("-Isrc/include")
        }
    }
    iosArm64("iosArm64Prd") {
        compilations["main"].cinterops.create("key") {
            defFile(project.file("src/nativeInterop/cinterop/ntstr.def"))
            includeDirs(project.file("src/include"))
            packageName("com.ryunen344.mpp.lib.nt")
            compilerOpts("-Isrc/include")
        }
    }
    iosX64("iosX64Dev") {
        compilations["main"].cinterops.create("key") {
            defFile(project.file("src/nativeInterop/cinterop/ntstr.def"))
            includeDirs(project.file("src/include"))
            packageName("com.ryunen344.mpp.lib.nt")
            compilerOpts("-Isrc/include")
        }
    }
    iosX64("iosX64Prd") {
        compilations["main"].cinterops.create("key") {
            defFile(project.file("src/nativeInterop/cinterop/ntstr.def"))
            includeDirs(project.file("src/include"))
            packageName("com.ryunen344.mpp.lib.nt")
            compilerOpts("-Isrc/include")
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
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64DevMain by getting {
            dependsOn(iosArm64Main)
        }
        val iosArm64PrdMain by getting {
            dependsOn(iosArm64Main)
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosX64DevMain by getting {
            dependsOn(iosX64Main)
        }
        val iosX64PrdMain by getting {
            dependsOn(iosX64Main)
        }
    }
}
android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        externalNativeBuild {
            cmake {
                arguments.plusAssign("-DCMAKE_BUILD_TYPE=DEBUG")
                cppFlags.plusAssign("-DBUILD_DEBUG")
                getcFlags() += "-DBUILD_DEBUG"
            }
        }

        flavorDimensions("env")
        productFlavors {
            create("dev") {
                dimension = "env"
            }
            create("prd") {
                dimension = "env"
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets.forEach {
        it.manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
    sourceSets {
        getByName("main").java.srcDirs("src/androidMain/kotlin")
        getByName("test").java.srcDirs("src/androidTest/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
    lintOptions {
        isAbortOnError = false
    }
    externalNativeBuild {
        cmake {
            setPath("src/androidMain/cpp/CMakeLists.txt")
            version = "3.10.2"
        }
    }
    ndkVersion = "21.0.6113669"
}

publishing {
    repositories {
        maven {
            name = "mpp-lib-test"
            url = uri("https://maven.pkg.github.com/RyuNen344/mpp-lib-test")
            credentials {
                username = "RyuNen344"
                password = ""
            }
        }
        maven("${project.rootDir}/releases")
    }
}
