apply(from = "../android-module.gradle")

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
}

dependencies {
    AndroidModule.main.forEach(::implementation)
    AndroidModule.unitTesting.forEach(::testImplementation)
    AndroidModule.androidTesting.forEach(::androidTestImplementation)
}
