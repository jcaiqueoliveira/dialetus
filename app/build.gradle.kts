import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)

    val currentVersion = Versioning.getVersion()

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)
        versionCode = currentVersion.code
        versionName = currentVersion.name
        testInstrumentationRunner = AndroidConfig.instrumentationTestRunner

        // archivesBaseName = "app-${currentVersion.name}"

        resConfigs("pt-rBR")

        vectorDrawables.apply {
            useSupportLibrary = true
            setGeneratedDensities(emptyList())
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            isCrunchPngs = false
            isMinifyEnabled = false

            applicationIdSuffix = ".dev"
            // lintOptions { tasks.lint.enabled = false }

            defaultConfig {
                resConfigs("xxxhdpi")
            }

            // https://stackoverflow.com/a/55745719
            (this@getByName as ExtensionAware).apply {
                extra["alwaysUpdateBuildId"] = false
                extra["enableCrashlytics"] = false
            }
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/main.kotlin_module")
        exclude("**/*.properties")
    }

    lintOptions {
        isAbortOnError = false
        isIgnoreWarnings = true
        isQuiet = true
        disable("InvalidPackage", "OldTargetApi")
    }

    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":data"))

    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpInterceptor)

    AndroidModule.main.forEach(::implementation)
    AndroidModule.unitTesting.forEach(::testImplementation)
    AndroidModule.androidTesting.forEach(::androidTestImplementation)
}

extensions.getByType<AndroidExtensionsExtension>().apply {
    isExperimental = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_1_8}"
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check")
    }
}
