apply(from = "../android-module.gradle")

plugins {
    id("com.android.library")
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":utils"))

    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutinesAndroid)

    AndroidModule.main.forEach(::implementation)
    AndroidModule.unitTesting.forEach(::testImplementation)
    AndroidModule.androidTesting.forEach(::androidTestImplementation)
}
