plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    `maven-publish`
}

group = "io.github.kmpauto"
version = "0.1.0-SNAPSHOT"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "KmpAutoCore"
            isStatic = true
            linkerOpts("-framework", "CarPlay")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.car.app)
            implementation(libs.kotlinx.coroutines.android)
        }

        androidUnitTest.dependencies {
            implementation(libs.androidx.car.app.testing)
        }
    }
}

android {
    namespace = "io.github.kmpauto.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("kmp-auto-core")
            description.set("Kotlin Multiplatform Library for Android Auto and Apple CarPlay")
            url.set("https://github.com/jesusdmedinac/kmp-auto")
        }
    }
}
