apply(from = "../kotlin-module.gradle")

plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Dependencies.coroutines)
    StandaloneModule.main.forEach(::implementation)
    StandaloneModule.unitTesting.forEach(::testImplementation)
}
